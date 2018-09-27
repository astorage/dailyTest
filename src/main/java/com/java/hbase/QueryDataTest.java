package com.java.hbase;

import com.java.util.JacksonUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

/**
 * Author: Boris
 * Date: 2018/9/7 10:38
 * Copyright (C), 2017-2018,
 * Description:
 */
public class QueryDataTest {
    private static String HBASE_NO_DATA = "keyvalues=NONE";

    /**
     * 配置ss
     */
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
        showOneRecordByRowKey_cloumn("user_category_push_data", "66666666@6", "user", "userIds");
    }

    public static void showOneRecordByRowKey_cloumn(String tableName,String rowkey,String columnFamily,String quafilier) throws Exception{
        Table table = connection.getTable(TableName.valueOf(tableName));
        long start = 0;
        long end = start;
        try {
            Get get = new Get(rowkey.getBytes());
            get.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes(quafilier)); ////根据主键查询某列簇中的quafilier列
            start = System.currentTimeMillis();
            Result result = table.get(get);
            end = System.currentTimeMillis();
            if ((null != result) && (!HBASE_NO_DATA.equals(result.toString()))) {
                try {
                    byte[] byteArray = result.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes(quafilier));
                    String byteStr = Bytes.toString(byteArray);
                    List<String> userIds = JacksonUtil.deserialize(byteStr, List.class);
                    System.out.println(byteStr);
                    System.out.println("userIds :" + userIds);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("数据库操作耗时：" + (end - start) + "毫秒");
    }
}
