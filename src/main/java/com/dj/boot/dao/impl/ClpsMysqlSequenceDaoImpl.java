package com.dj.boot.dao.impl;

/**
 * ID序列生成Dao层实现，默认初始值为25亿
 */

import com.dj.boot.dao.SequenceDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

public class ClpsMysqlSequenceDaoImpl implements SequenceDao {

	public static final long DEFAULT_MINIMUM_ID = 2500000000L;

	/**
	 * DataSource
	 */
	private DataSource dataSource;

	/**
	 * spring提供的jdbc操作类
	 */
	private JdbcTemplate jdbcTemplate;

	/*
	 * 事务模板
	 */
	private TransactionTemplate transactionTemplate;

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	/**
	 * 设置数据源
	 * 
	 * @param dataSource
	 * @return
	 */
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * 获取当前的ID序列
	 * 
	 * @param tableName
	 * @return
	 */
	@Override
	public long insertAndGetCurrentId(final String tableName) {
		long key = transactionTemplate.execute(new TransactionCallback<Long>() {
			@Override
			public Long doInTransaction(TransactionStatus status) {
				// 要获取的ID值
				long currentId = DEFAULT_MINIMUM_ID;
				// 更新结果
				int updateResult = 0;
				updateResult = jdbcTemplate.update("update sequence set id=LAST_INSERT_ID(`id` + 1) where tname=?",
						new Object[] { tableName }, new int[] { java.sql.Types.VARCHAR });
				// 查询最后更新的ID值
				if (updateResult > 0) {
					currentId = (long) jdbcTemplate.queryForObject(
							"select LAST_INSERT_ID(`id`) from sequence where tname=?", new Object[] { tableName },
							Long.class);
				}
				return currentId;
			}
		});
		return key;
	}

	/**
	 * 初始化一个新表
	 * 
	 * @param 表名
	 * @return
	 */
	@Override
	public int insertAndInitTable(String tableName) {
		return jdbcTemplate.update(
				"insert into sequence(tname,id) values(?," + DEFAULT_MINIMUM_ID + "); ",
				new Object[] { tableName }, new int[] { java.sql.Types.VARCHAR });
	}
}
