package com.dj.boot.common.util.db;


import com.dj.boot.common.util.es.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DBManager<T>
{

    private final static Logger logger = LoggerFactory.getLogger(DBManager.class);

    private static String DRIVER = "com.mysql.jdbc.Driver";

    private static String URL = "jdbc:mysql://localhost:3306/1804?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";

    private static String USERNAME = "root";

    private static String PASSWORD = "123456";
    
    private static Connection conn = null;
    
    private static DBManager instance = null;
    
    static{
//        Properties ps = new Properties();
//		try {
//			ps.load(DBManager.class.getClassLoader().getResourceAsStream("db.properties"));
//		} catch (IOException e) {
//			throw new RuntimeException("加载数据库配置失败",e);
//		}
		//logger.info("加载数据库配置:"+ps);
		DRIVER=PropertiesUtil.getProperty("prop/db.properties", "jdbc.driver");
		URL=PropertiesUtil.getProperty("prop/db.properties", "jdbc.url");
		USERNAME=PropertiesUtil.getProperty("prop/db.properties", "jdbc.username");
		PASSWORD=PropertiesUtil.getProperty("prop/db.properties", "jdbc.password");
    }
    
    public static synchronized DBManager getInstance()
    {
        if (null == instance)
        {
            DBManager manager = new DBManager();
            manager.init();
            instance = manager;
        }
        return instance;
    }
    
    private DBManager()
    {
        
    }
    
    private void init()
    {
        conn = null;
        try
        {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (Exception e)
        {
        	throw new RuntimeException(e);
        }
    }
    
    public void closeConn()
    {
        if (null != conn)
        {
            try
            {
                conn.close();
            }
            catch (SQLException e)
            {
                logger.error("系统异常：{}",e);
                //e.printStackTrace();
            }
        }
    }
    
    /**
     * 执行insert update delete SQl
     * 
     * @param sql SQL语句
     * @param params 参数列表
     * @return
     */
    public int executeSQL(String sql, Object... params)
    {
        logger.info(String.format("executeSQL:" + sql.replace("?", "%s"), params));
        PreparedStatement ps = null;
        int rows = 0;
        try
        {
            ps = conn.prepareStatement(sql);
            if (params != null && params.length > 0)
            {
                for (int i = 0; i < params.length; i++)
                {
                    ps.setObject(i + 1, params[i]);
                }
            }
            rows = ps.executeUpdate();
        }
        catch (SQLException e)
        {
            logger.error("系统异常：{}",e);
            //e.printStackTrace();
        }
        return rows;
    }
    
    /**
     * 根据Select查询产生Object对象
     * 
     * @param sql
     * @param map
     * @param params
     * @return
     */
    public <T> T queryForObject(String sql, IRowMap<T> map, Object... params)
    {
        logger.info(String.format("executeSQL:" + sql.replace("?", "%s"), params));
        T obj = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            ps = conn.prepareStatement(sql);
            if (params != null && params.length > 0)
            {
                for (int i = 0; i < params.length; i++)
                {
                    ps.setObject(i + 1, params[i]);
                }
            }
            rs = ps.executeQuery();
            if (rs.next())
            {
                obj = map.mapRow(rs);
            }
        }
        catch (SQLException e)
        {
            logger.error("系统异常：{}",e);
            //e.printStackTrace();
        }
        finally
        {
            close(rs, ps);
        }
        return obj;
    }
    
    /**
     * 根据SQL查询 返回int类型结果
     * 
     * @param sql
     * @param params
     * @return
     */
    public int queryForInt(String sql, Object... params)
    {
        logger.info(String.format("executeSQL:" + sql.replace("?", "%s"), params));
        int obj = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            ps = conn.prepareStatement(sql);
            
            if (params != null && params.length > 0)
            {
                for (int i = 0; i < params.length; i++)
                {
                    ps.setObject(i + 1, params[i]);
                }
            }
            rs = ps.executeQuery();
            if (rs.next())
            {
                obj = rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            logger.error("系统异常：{}",e);
            // e.printStackTrace();
        }
        finally
        {
            close(rs, ps);
        }
        return obj;
    }
    
    /**
     * 根据Select查询产生List集合
     * 
     * @param sql
     * @param map
     * @param params
     * @return
     */
    public List<T> queryForList(String sql, IRowMap<T> map, Object... params)
    {
        logger.info(String.format("executeSQL:" + sql.replace("?", "%s"), params));
        List<T> list = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            ps = conn.prepareStatement(sql);
            
            if (params != null && params.length > 0)
            {
                for (int i = 0; i < params.length; i++)
                {
                    ps.setObject(i + 1, params[i]);
                }
            }
            rs = ps.executeQuery();
            while (rs.next())
            {
                if (list == null)
                {
                    list = new ArrayList<T>();
                }
                T obj = map.mapRow(rs);
                list.add(obj);
            }
        }
        catch (SQLException e)
        {
            logger.error("系统异常：{}",e);
            //e.printStackTrace();
        }
        finally
        {
            close(rs, ps);
        }
        return list;
    }
    
    /** 取序列 */
    public Long getNextSeqValue(String seqName){
    	return (long)queryForInt(String.format("select customer.%s.nextval from dual",seqName));
    }
    
    /**
     * 关闭资源
     * 
     * @param ps
     * @param conn
     */
    private void close(PreparedStatement ps, Connection conn)
    {
        if (ps != null)
        {
            try
            {
                ps.close();
            }
            catch (SQLException e)
            {
                logger.error("系统异常：{}",e);
                //e.printStackTrace();
            }
            finally
            {
                if (conn != null)
                {
                    try
                    {
                        conn.close();
                    }
                    catch (SQLException e)
                    {
                        logger.error("系统异常：{}",e);
                        //e.printStackTrace();
                    }
                }
            }
        }
    }
    
    /**
     * 关闭资源
     * 
     * @param rs
     * @param ps
     */
    public void close(ResultSet rs, PreparedStatement ps)
    {
        if (rs != null)
        {
            try
            {
                rs.close();
            }
            catch (SQLException e)
            {
                logger.error("系统异常：{}",e);
                //e.printStackTrace();
            }
            finally
            {
                if (ps != null)
                {
                    try
                    {
                        ps.close();
                    }
                    catch (SQLException e)
                    {
                        logger.error("系统异常：{}",e);
                        //e.printStackTrace();
                    }
                }
            }
        }
    }
    
    public static interface IRowMap<T>
    {
        T mapRow(ResultSet rs)
            throws SQLException;
    }
}
