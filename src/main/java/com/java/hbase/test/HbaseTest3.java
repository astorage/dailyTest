//package com.java.hbase.test;
//
//import com.java.hbase.HBaseUtil;
//import org.apache.hadoop.hbase.HTableDescriptor;
//import org.apache.hadoop.hbase.client.HBaseAdmin;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Author: Boris
// * Date: 2018/8/31 14:34
// * Copyright (C), 2017-2018,
// * Description:
// */
//public class HbaseTest3 {
//
//    public static void main(String[] args)  throws Exception{
//        getAllTables();
//    }
//
//    // Hbase获取所有的表信息
//    public static void getAllTables() throws Exception{
//        // 创建表管理类
//        HBaseAdmin admin = new HBaseAdmin(HBaseUtil.getConfig()); // hbase表管理
//        List<String> tables = null;
//        if (admin != null) {
//            try {
//                HTableDescriptor[] allTable = admin.listTables();
//                if (allTable.length > 0)
//                    tables = new ArrayList<String>();
//                for (HTableDescriptor hTableDescriptor : allTable) {
//                    tables.add(hTableDescriptor.getNameAsString());
//                    System.out.println(hTableDescriptor.getNameAsString());
//                }
//            }catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println(tables);
//    }
//}
