package com.yh;

import org.junit.After;
import org.junit.Before;

import java.sql.*;

/**
 * <p> Description:           </p>
 *
 * @Author ranshaolin
 * @Date 2019/11/27
 * @Version
 * @修改记录 <pre>
 *
 * 版本号   修改人       修改时间          修改内容描述
 * -------------------------------------------------------------
 * V1.0		  冉绍林	      2019/11/27	       新建文件
 *
 * </pre>
 */
public class PhoenixBaseTest {
    protected Connection connection = null;
    protected Statement statement = null;
    protected ResultSet rs = null;

    @Before
    public void before(){
        //第一步：注册驱动
        try {
            Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
            //第二步：获取连接
            String url = "jdbc:phoenix:node1,node2,node3:2181";
            //String url = "jdbc:phoenix:localhost";
            // String url =
            // "jdbc:phoenix:41.byzoro.com,42.byzoro.com,43.byzoro.com:2181";
            long startTime = System.currentTimeMillis();
            connection = DriverManager.getConnection(url);
            long endTime = System.currentTimeMillis();
            System.out.println("连接耗时："+(endTime-startTime) + "ms");
            statement = connection.createStatement();
            System.out.println("连接成功！！！\n");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭资源，注意关闭顺序
     */
    @After
    public void after(){
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }

            if (statement != null && !statement.isClosed()) {
                statement.close();
            }

            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("\n资源关闭成功！！！");

    }
}
