/*
package com.java.hbase.test;

import com.java.hbase.HBaseUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;

import java.io.IOException;

*/
/**
 * Author: Boris
 * Date: 2018/8/31 21:44
 * Copyright (C), 2017-2018,
 * Description:
 *//*

public class HbaseTest6 {

    */
/**
     * 配置ss
     *//*

    private static Connection connection = null;
    private static Table table = null;


    static {
        try {
            connection = HBaseUtil.getConnection();
            table = connection.getTable(TableName.valueOf("dept_test"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    */
/**
     * 部分代码来自hbase权威指南
     * @throws IOException
     *//*

    public void testRowFilter() throws IOException {
        String tableName = "dept_test";
        Configuration config = HBaseUtil.getConfig();
        Table table = new HTable(config, tableName);
        Scan scan = new Scan();

        System.out.println("小于等于row010的行");
        Filter filter1 = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL,
                new BinaryComparator("row010".getBytes()));
        scan.setFilter(filter1);
        ResultScanner scanner1 = table.getScanner(scan);
        for (Result res : scanner1) {
            System.out.println(res);
        }
        scanner1.close();

        System.out.println("正则获取结尾为5的行");
        Filter filter2 = new RowFilter(CompareFilter.CompareOp.EQUAL,
                new RegexStringComparator(".*5"));
                        scan.setFilter(filter2);
        ResultScanner scanner2 = table.getScanner(scan);
        for (Result res : scanner2) {
            System.out.println(res);
        }
        scanner2.close();

        System.out.println("包含有5的行");
        Filter filter3 = new RowFilter(CompareFilter.CompareOp.EQUAL,
                new SubstringComparator("5"));
        scan.setFilter(filter3);
        ResultScanner scanner3 = table.getScanner(scan);
        for (Result res : scanner3) {
            System.out.println(res);
        }
        scanner3.close();

        System.out.println("开头是row01的");
        Filter filter4 = new RowFilter(CompareFilter.CompareOp.EQUAL,
                new BinaryPrefixComparator("row01".getBytes()));
        scan.setFilter(filter4);
        ResultScanner scanner4 = table.getScanner(scan);
        for (Result res : scanner4) {
            System.out.println(res);
        }
        scanner3.close();
    }
}
*/
