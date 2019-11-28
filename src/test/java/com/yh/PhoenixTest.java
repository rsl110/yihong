package com.yh;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 各种表操作语法参考官方地址：https://phoenix.apache.org/language/index.html
 *
 *
 *
 * ====phoenix表映射：=====
 * 默认情况下，直接在hbase中创建的表，通过phoenix是查看不到的，
 * 如：表US_POPULATION是在phoenix中直接创建的，而test是在hbase中直接创建的，
 * 所以默认情况下，在phoenix中是查看不到test的。
 * <p>
 * 如果需要在phoenix中操作直接在hbase中创建的表，则需要在phoenix中进行表的映射。
 * 映射方式有两种：视图映射和表映射。
 */
public class PhoenixTest extends PhoenixBaseTest {


    /**
     * 创建表
     */
    @Test
    public void createTable() {
        try {
            String tableName = "my_test";
            String sql = "create table IF NOT EXISTS " + tableName + " (mykey integer not null primary key, mycolumn varchar)";
            statement.executeUpdate(sql);
            connection.commit();
            System.out.println("表创建成功！！！");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 向表中插入数据
     * Phoenix中不存在 insert 的语法关键字，而是 upsert ，功能上替代了 Insert+update，
     * <p>
     * <p>
     * JDBC插入语句格式：        insert into Person (IDCardNum,Name,Age) values (100,'小明',12);
     * <p>
     * Phoenix中插入语句格式 ：upsert into test.Person (IDCardNum,Name,Age) values (100,'小明',12);
     */
    @Test
    public void insert() {
        try {
            String insertSql = "upsert into test values (1,'Hello')";
            statement.executeUpdate(insertSql);
            connection.commit();
            System.out.println("数据插入成功！！！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新表数据
     * <p>
     * Phoenix中不存在 update 的语法关键字，而是 upsert ，功能上替代了 Insert+update，
     *
     * @param
     */
    @Test
    public void update() {
        try {
            String updateSql = "upsert into test values (2,'香菇')";
            statement.executeUpdate(updateSql);
            connection.commit();
            System.out.println("更新成功！！！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询表数据 ：
     *
     * @param
     * @return
     */
    @Test
    public void select() {
        try {
            String selectSql = "select * from test";
            statement = (PreparedStatement) connection.prepareStatement(selectSql);
            rs = ((PreparedStatement) statement).executeQuery();
            System.out.println("数据查询成功！！！");
            while (rs.next()) {
                System.out.println(rs.getInt("mykey") + " " + rs.getString("mycolumn"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除表数据：和 mysql 的标准 sql 语法一样
     */
    @Test
    public void delete() {
        try {
            String deleteSql = "delete from test where mykey = 1";
            statement.executeUpdate(deleteSql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("删除成功！！！");

    }
}


