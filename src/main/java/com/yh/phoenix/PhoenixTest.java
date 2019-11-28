package com.yh.phoenix;

import java.sql.*;

/**
 *
 */
public class PhoenixTest {


    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            //第一步：注册驱动
            Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
            // 这里配置zookeeper的地址，可单个，也可多个。可以是域名或者ip

            //第二步：获取连接
            String url = "jdbc:phoenix:node1,node2,node3:2181";
            //String url = "jdbc:phoenix:localhost";
            // String url =
            // "jdbc:phoenix:41.byzoro.com,42.byzoro.com,43.byzoro.com:2181";
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            statement.executeUpdate("create table IF NOT EXISTS test (mykey integer not null primary key, mycolumn varchar)");
            statement.executeUpdate("upsert into test values (1,'Hello')");
            statement.executeUpdate("upsert into test values (2,'World!')");
            connection.commit();
            statement = (PreparedStatement) connection.prepareStatement("select * from test");
            rs = ((PreparedStatement) statement).executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("mykey") + " " +rs.getString("mycolumn"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
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
        }
    }



}
