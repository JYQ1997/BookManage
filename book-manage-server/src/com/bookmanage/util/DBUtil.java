package com.bookmanage.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class DBUtil {

    /**
     * 连接数据库的用户名
     */
    static String user = null;
    /**
     * 连接数据库的密码
     */
    static String password = null;
    /**
     * 连接数据库的地址
     */
    static String url = null;
    /**
     * 连接数据库的驱动
     */
    static String driver = null;

    static {
//获取输入流
        InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
        if (in == null) {
            try {
                throw new Exception("加载错误，无法找到连接属性资源");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//获取properties对象
        Properties p = new Properties();

        try {
//读取配置文件 获取数据库连接信息
            p.load(in);
            user = p.get("user").toString();
            password = p.get("password").toString();
            url = p.get("url").toString();
            driver = p.get("driver").toString();

            //注册驱动
            Class.forName(driver);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取配置文件失败！！！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("注册驱动失败！！！");
        }
    }


    /**
     * 连接数据库
     *
     * @return java.sql.Connection
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
//连接数据库
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败！！！");
        }
        return conn;
    }


    /**
     * 关闭资源
     *
     * @param conn java.sql.Connection
     * @param stas java.sql.Statement
     * @param rs   java.sql.ResultSet
     */
    public static void close(Connection conn, Statement stas, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stas != null) stas.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
