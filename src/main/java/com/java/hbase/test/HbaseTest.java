package com.java.hbase.test;

import com.java.hbase.HBaseUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Author: Boris
 * Date: 2018/8/31 10:17
 * Copyright (C), 2017-2018,
 * Description:
 */
public class HbaseTest {

    // hbase无数据时返回的字符串
    private static String HBASE_NO_DATA = "keyvalues=NONE";
    private static Connection connection;

    static {
        try {
            connection = HBaseUtil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{

        test1();
//        String tableName = "emp";
//        String rowKey = "1";
        //queryByRowKey(rowKey, tableName);

        //query(tableName);
    }


    private static void test1() throws Exception{
        String tableName = "user_category_push_data";
        String rowKey = "777777777@1";
        //String rowKey = "null@1";
        String cf = "user";
        String column = "userList";

        Result result = HBaseUtil.get(tableName, rowKey, cf, column);

        if ((null != result) && (!HBASE_NO_DATA.equals(result.toString()))) {
            try {
                byte[] ctiy = result.getValue(Bytes.toBytes(cf), Bytes.toBytes(column));
                String byteStr = Bytes.toString(ctiy);
                System.out.println(byteStr);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void test2()throws Exception {
        String tableName = "user_category_push_data";
        String rowKey = "777777777@1";
        //String rowKey = "null@1";
        String cf = "user";
        String column = "userList";

    }

    /**
     * 查询表信息
     * @param tableName
     */
    public static void query(String tableName){
        HTable hTable = null;
        ResultScanner scann = null;
        try {
            hTable = (HTable) connection.getTable(TableName.valueOf(tableName));
            scann = hTable.getScanner(new Scan());
            for(Result rs : scann){
                System.out.println("RowKey为："+new String(rs.getRow()));
                //按cell进行循环
                for(Cell cell : rs.rawCells()){
                    System.out.println("列簇为："+new String(CellUtil.cloneFamily(cell)));
                    System.out.println("列修饰符为："+new String(CellUtil.cloneQualifier(cell)));
                    System.out.println("值为："+new String(CellUtil.cloneValue(cell)));
                }
                System.out.println("=============================================");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(scann!=null){
                scann.close();
            }
            if(hTable!=null){
                try {
                    hTable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            HBaseUtil.close();
        }
    }



    /**
     * 根据rowkey查询单行
     * @param key
     * @param tableName
     */
    public static void queryByRowKey(String key,String tableName)throws Exception{
        HTable hTable = null;
        try {
            hTable = (HTable) connection.getTable(TableName.valueOf(tableName));

            Result rs = hTable.get(new Get(Bytes.toBytes(key)));
            System.out.println(tableName+"表RowKey为"+key+"的行数据如下：");
            for(Cell cell : rs.rawCells()){
                System.out.println("列簇为："+new String(CellUtil.cloneFamily(cell)));
                System.out.println("值为："+new String(CellUtil.cloneValue(cell)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(hTable!=null){
                try {
                    hTable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            HBaseUtil.close();
        }
    }



}

