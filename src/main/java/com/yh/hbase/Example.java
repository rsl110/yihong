package com.yh.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;

/**
 * <p> Description:           </p>
 *
 * @Author ranshaolin
 * @Date 2019/11/26
 * @Version
 * @修改记录 <pre>
 *
 * 版本号   修改人       修改时间          修改内容描述
 * -------------------------------------------------------------
 * V1.0		  冉绍林	      2019/11/26	       新建文件
 *
 * </pre>
 */
public class Example {
    private static final String TABLE_NAME = "MY_TABLE_NAME_TOO";
    private static final String CF_DEFAULT = "DEFAULT_COLUMN_FAMILY";
    public static void createOrOverwrite(Admin admin, HTableDescriptor table) throws IOException {
        if(admin.tableExists(table.getTableName())){
            admin.disableTable(table.getTableName());
            admin.deleteTable(table.getTableName());
        }
        admin.createTable(table);
    }
    public static void createSchemaTables(Configuration config) throws IOException {
        try(Connection connection = ConnectionFactory.createConnection() ;    Admin admin = connection.getAdmin()){
            HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
//            hTableDescriptor.addFamily(new HColumnDescriptor(CF_DEFAULT)).
        }
    }


    public static void main(String[] args) {

    }
}
