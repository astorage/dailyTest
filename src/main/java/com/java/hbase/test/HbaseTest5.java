/*
package com.java.hbase.test;

import com.java.hbase.HBaseUtil;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

*/
/**
 * Author: Boris
 * Date: 2018/8/31 21:23
 * Copyright (C), 2017-2018,
 * Description:
 *//*

public class HbaseTest5 {
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


    public static void main(String[] args)throws  Exception {
        //showOneRecordByRowKey_cloumn("dept_test", "0_1", "subdept");
        showOneRecordByRowKey_cloumn("dept_test", "0_1", "subdept", "subdept2");
    }

    */
/**
     * 根据rowkey,一行中的某一列簇查询一条数据
     * get 'student','010','info'
     * student sid是010的info列簇（info:age,info:birthday）
     * <p>
     * get 'student','010','info:age'
     * student sid是010的info:age列,quafilier是age
     *//*

    //public static void showOneRecordByRowKey_cloumn(String tableName,String rowkey,String column,String quafilier)
    public static void showOneRecordByRowKey_cloumn(String tableName, String rowkey, String columnFamily) throws Exception{
        System.out.println("start===根据主键查询某列簇showOneRecordByRowKey_cloumn");

        Table table = connection.getTable(TableName.valueOf(tableName));

        try {
            Get get = new Get(rowkey.getBytes());
            get.addFamily(columnFamily.getBytes()); //根据主键查询某列簇
            //get.addColumn(Bytes.toBytes(column),Bytes.toBytes(quafilier)); ////根据主键查询某列簇中的quafilier列
            Result r = table.get(get);

            for (KeyValue kv : r.raw()) {
                System.out.println("KeyValue---" + kv);
                System.out.println("row=>" + new String(kv.getRow()));
                System.out.println("family=>" + new String(kv.getFamily(), "utf-8") );
                System.out.println("qualifier=>" + new String(kv.getQualifier()) + ": " + new String(kv.getValue(), "utf-8") );

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("end===========showOneRecordByRowKey_cloumn");

    }

    public static void showOneRecordByRowKey_cloumn(String tableName,String rowkey,String columnFamily,String quafilier) throws Exception{
        System.out.println("start===根据主键查询某列簇showOneRecordByRowKey_cloumn");

        Table table = connection.getTable(TableName.valueOf(tableName));

        try {
            Get get = new Get(rowkey.getBytes());
            //get.addFamily(columnFamily.getBytes()); //根据主键查询某列簇
            get.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes(quafilier)); ////根据主键查询某列簇中的quafilier列
            Result r = table.get(get);

            for (KeyValue kv : r.raw()) {
                System.out.println("KeyValue---" + kv);
                System.out.println("row=>" + new String(kv.getRow()));
                System.out.println("family=>" + new String(kv.getFamily(), "utf-8") );
                System.out.println("qualifier=>" + new String(kv.getQualifier()) + ": " + new String(kv.getValue(), "utf-8") );

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("end===========showOneRecordByRowKey_cloumn");
    }

}
*/
