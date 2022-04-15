package com.dj.boot.test.mybatistest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.test.mybatistest
 * @Author: wangJia
 * @Date: 2021-10-27-20-32
 */
public class JDBCTest {
    /**
     * 数据库地址 替换成本地的地址
     */
    private static final String url = "jdbc:mysql://localhost:3306/1804?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";
    /**
     * 数据库用户名
     */
    private static final String username = "root";
    /**
     * 密码
     */
    private static final String password = "123456";

    public static void main(String[] args) {
        try {
            // 1. 加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 2. 获得连接
            Connection connection = DriverManager.getConnection(url, username, password);
            // 3. 创建sql语句执行平台
            String sql = "select * from dj_user";
            Statement statement = connection.createStatement();
            // 4. 执行sql
            ResultSet result = statement.executeQuery(sql);
            // 5. 处理结果
            while(result.next()){
                System.out.println("result = " + result.getString(1));
            }
            // 6. 关闭连接
            result.close();
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }

    }

}
