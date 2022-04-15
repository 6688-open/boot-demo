/*
 * File : Dialect.java
 * date : Sep 18, 2012 9:48:34 AM
 */
package com.dj.boot.configuration.interceptor.dao;

import java.util.LinkedHashMap;

/**
* [添加说明]
* <br>Version 1.0
*/
public abstract class Dialect {

	public static enum Type {
		MYSQL, ORACLE
	}

	public abstract String getLimitString(String sql, int skipResults, int maxResults);
	
	public abstract String getCountString(String sql);
	
	public abstract String getOrderString(String sql, LinkedHashMap<String, String> taskItems);

}
