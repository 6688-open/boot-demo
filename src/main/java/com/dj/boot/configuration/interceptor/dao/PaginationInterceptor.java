package com.dj.boot.configuration.interceptor.dao;
/**
* 此拦截器可实现数据列表的排序及物理分页
* <br>Version 1.0
*/

import com.dj.boot.btp.common.util.noUtil.Constant;
import com.dj.boot.common.base.page.PageRequestParam;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) }) //3.4之后多了一个Interger.class的参数
public class PaginationInterceptor implements Interceptor {

	 private static final Logger log = LogManager.getLogger(PaginationInterceptor.class);

	/* (non-Javadoc)  
     * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		//mybaits提供了MetaObject类，该类对反射进行封装。可以通过该类快速的实现获取与设置delegate.boundSql.sql。
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
		RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
		String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
		Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
		//获取参数 判断入参是否有page分页参数
		DefaultParameterHandler defaultParameterHandler = (DefaultParameterHandler) metaStatementHandler.getValue("delegate.parameterHandler");
		PageRequestParam page = null;
		Object object = defaultParameterHandler.getParameterObject();
		if(object instanceof HashMap){
			Map parameterObject = (Map)object;
			if(parameterObject.containsKey(Constant.PAGE)){
				 page = (PageRequestParam)parameterObject.get(Constant.PAGE);
			}
		}else{
			if(object  instanceof PageRequestParam){
				page = (PageRequestParam)object;
			}
		}
		if(null == page){
			return invocation.proceed();
		}
		//根据配置路由MYSQL/ORACLE
		Dialect.Type databaseType = null;
		try {
			databaseType = Dialect.Type.valueOf(configuration.getVariables().getProperty("dialect").toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (databaseType == null) {
			throw new RuntimeException("the value of the dialect property in configuration.xml is not defined : "
					+ configuration.getVariables().getProperty("dialect"));
		}
		Dialect dialect = null;
		switch (databaseType) {
			case ORACLE:
				dialect = new OracleDialect();
				break;
			case MYSQL:
				dialect = new MySqlDialect();
				break;
		}
		Connection connection = (Connection) invocation.getArgs()[0];
		BoundSql boundSql = (BoundSql) statementHandler.getBoundSql();

		MappedStatement mappedStatement = (MappedStatement)metaStatementHandler.getValue("delegate.mappedStatement");
		//截取sql 填充count(1) 查询数量
		String countSql = dialect.getCountString(originalSql);
		//排序
		if (page.getSortColName() != null) {
				@SuppressWarnings("unchecked")
                LinkedHashMap<String, String> sorts = new LinkedHashMap<String, String>();
				sorts.put( getColumnsByPropertyName(mappedStatement,page.getSortColName()), page.getSortDir());
				//使用自定义排序
				originalSql = dialect.getOrderString(originalSql, sorts);
		}

		//使用自定义的物理分页方法。若不使用自定义分页（此处注释该方法），则使用mybatis的逻辑分页。物理分页效率高于逻辑分页
		originalSql = dialect.getLimitString(originalSql, page.getiDisplayStart(), page.getiDisplayLength());
		//如果使用自定义的物理分页法，请打开下边的两个注释。
		metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
		metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
		
		metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
		//根据截取的countSql 自定义查询数量的BoundSql 执行sql 返回的数量封装到page入参里面
		setPageParameter(connection, countSql, mappedStatement,  boundSql,  page);
		log.debug("生成分页SQL : " + boundSql.getSql());

		return invocation.proceed();
	}

	/**
	 * 解析的mapper.xml resultMap里面的映射信息会放到MappedStatement 里面的属性里 根据参数是否有Property 没有默认id
	* @Description:  
	* @param mappedStatement
	* @param sortColName
	* @return 
	*/
	private String getColumnsByPropertyName(MappedStatement mappedStatement, String sortColName) {
		 ResultMap resultMaps = (ResultMap)mappedStatement.getResultMaps().get(0);
		 List<ResultMapping> resultMappings = (List<ResultMapping>)resultMaps.getPropertyResultMappings();
		 for(ResultMapping mapping:resultMappings){
			 if(sortColName.equals(mapping.getProperty())){
				 return mapping.getColumn();
			 }
		 }
		 return "id";
	}

	/**
	 * 从数据库里查询总的记录数并计算总页数，回写进分页入参里面,这样调用
	 * 者就可用通过分页入参获取信息
	 *  
	 * @param connection
	 * @param mappedStatement 
	 * @param boundSql 
	 * @param page 
	 */  
	private void setPageParameter(Connection connection, String countSql, MappedStatement mappedStatement,
                                  BoundSql boundSql, PageRequestParam page) {
	    // 记录总记录数  
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	    try {
			statement = connection.prepareStatement(countSql);
	        
	        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
	        
	        BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
	        		parameterMappings, boundSql.getParameterObject());  
	        
	        if(parameterMappings!=null && parameterMappings.size()>0){
	        	for (ParameterMapping parameterMapping:parameterMappings) {
					 Object propertyValue = null;
		        	 String propertyName = parameterMapping.getProperty();
		        	 PropertyTokenizer prop = new PropertyTokenizer(propertyName);
		        	 if(boundSql.hasAdditionalParameter(propertyName)){
		        		 propertyValue =  boundSql.getAdditionalParameter(propertyName);
		        		 countBS.setAdditionalParameter(propertyName, propertyValue);
		        	 }else if(propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())){
		        		 propertyValue =  boundSql.getAdditionalParameter(prop.getName());
		        		 countBS.setAdditionalParameter(propertyName, propertyValue);
		        	 }
				}
	        }
	        setParameters(statement, mappedStatement, countBS, boundSql.getParameterObject());
	        rs = statement.executeQuery();
	        int totalCount = 0;  
	        if (rs.next()) {  
	            totalCount = rs.getInt(1);  
	        }  
	        page.setiTotalRecords(totalCount);  
	    } catch (SQLException e) {
	    	log.error("Ignore this exception", e);  
	    } finally {  
	        try {  
	            rs.close();  
	        } catch (SQLException e) {
	        	log.error("Ignore this exception", e);  
	        }  
	        try {
				statement.close();
	        } catch (SQLException e) {
	        	log.error("Ignore this exception", e);  
	        }  
	    }  
	}  
	  
	/** 
	 * 对SQL参数(?)设值 
	 *  
	 * @param ps 
	 * @param mappedStatement 
	 * @param boundSql 
	 * @param parameterObject 
	 * @throws SQLException
	 */  
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
		//handler.parameterize 其实就是调用的 handler.parameterize
	    ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
	    parameterHandler.setParameters(ps);  
	}  

	/* (non-Javadoc)  
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)  
	 */
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/* (non-Javadoc)  
	 * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)  
	 */
	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub    

	}

}