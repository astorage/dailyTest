//package com.java.hbase.test;
//
//import com.java.hbase.HBaseUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.hadoop.hbase.*;
//import org.apache.hadoop.hbase.client.*;
//import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
//import org.apache.hadoop.hbase.filter.*;
//import org.apache.hadoop.hbase.util.Bytes;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Author: Boris
// * Date: 2018/8/31 15:07
// * Copyright (C), 2017-2018,
// * Description:
// */
//public class HbaseTest4 {
//
//    /**
//     * 配置ss
//     */
//    private static Connection connection = null;
//    private static String tableName = "dept_test";
//    private static String rowKey = "1";
//    private static String columnfamily = "personal data";
//    private static String column = "city";
//
//    static {
//        try {
//            connection = HBaseUtil.getConnection();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public static void main(String[] args) throws Exception{
//        selectOneByRowKey(tableName, rowKey);
//
//        //selectAllByPage(tableName, 5);
//    }
//
//    /**
//     * 查询单条记录
//     *
//     * @param tableName emp
//     *            表名
//     * @param rowKey
//     *            rowKey值
//     * @return 返回单条记录
//     */
//    public static List<Map<String, String>> selectOneByRowKey(String tableName, String rowKey) {
//        if (StringUtils.isBlank(rowKey) || StringUtils.isBlank(tableName)) {
//            System.out.println("===Parameters tableName|rowKey should not be blank,Please check!===");
//            return null;
//        }
//        List<Map<String, String>> rowList = null;
//        try {
//            Get get = new Get(Bytes.toBytes(rowKey));
//            Table hTable = connection.getTable(TableName.valueOf(tableName));
//            if (hTable != null) {
//                Result result = hTable.get(get);
//                rowList = getRowByResult1(result);
//            }
//            hTable.close();
//        } catch (IOException e) {
//           e.printStackTrace();
//        }
//        System.out.println(rowList);
//        HBaseUtil.close();
//        return rowList;
//    }
//
//
//    /**
//     * 分页查询表数据
//     *
//     * @param tableName
//     *            表名
//     * @param pageSize
//     *            页大小
//     * @return 返回查询数据结果集
//     */
//    public static List<Map<String, String>> selectAllByPage(String tableName, int pageSize) throws Exception{
//        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(pageSize + "")) {
//            System.out.println("===Parameters tableName|ddate|pageSize|rowKey should not be blank,Please check!===");
//            return null;
//        }
//        Table hTable = connection.getTable(TableName.valueOf(tableName));
//        Scan scan = new Scan();
//        FilterList filterList = new FilterList(
//                FilterList.Operator.MUST_PASS_ALL);
//        Filter pageFilter = new PageFilter(pageSize);
//        filterList.addFilter(pageFilter);
//        scan.setFilter(filterList);
//        List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
//        try {
//            ResultScanner rs = hTable.getScanner(scan);
//            for (Result result : rs) {
//                lists.add(getRowByResult(result));
//            }
//            hTable.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            System.out.println(e);
//        }
//
//        for (Map<String, String> map : lists) {
//            System.out.println(map);
//        }
//        return lists;
//    }
//
//    /**
//     * 根据状态分页查询表数据
//     *
//     * @param tableName
//     *            表名
//     * @param ddate
//     *            数据日期
//     * @param pageSize
//     *            页大小
//     * @param lastrowKey
//     *            起始rowkey值
//     * @param status
//     *            发送状态
//     * @return 返回查询数据结果集
//     */
//    public List<Map<String, String>> selectAllByPageStatus(String tableName,
//                                                           String ddate, int pageSize, String lastrowKey, String status) throws Exception{
//        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(ddate)
//                || StringUtils.isBlank(pageSize + "")
//                || StringUtils.isBlank(lastrowKey)) {
//            System.out.println("===Parameters tableName|ddate|pageSize|rowKey should not be blank,Please check!===");
//            return null;
//        }
//        Table hTable = connection.getTable(TableName.valueOf(tableName));
//        Scan scan = new Scan();
//        FilterList filterList = new FilterList(
//                FilterList.Operator.MUST_PASS_ALL);
//        filterList
//                .addFilter(new SingleColumnValueFilter(Bytes.toBytes("info"),
//                        Bytes.toBytes("status"), CompareFilter.CompareOp.EQUAL, Bytes
//                        .toBytes(status)));
//        Filter rowFilter1 = new RowFilter(CompareFilter.CompareOp.EQUAL,
//                new SubstringComparator(ddate));
//        Filter pageFilter = new PageFilter(pageSize);
//        filterList.addFilter(rowFilter1);
//        filterList.addFilter(pageFilter);
//
//        scan.setFilter(filterList);
//        List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
//        try {
//            ResultScanner rs = hTable.getScanner(scan);
//            for (Result result : rs) {
//                lists.add(getRowByResult(result));
//            }
//            hTable.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            System.out.println(e);
//        }
//        return lists;
//    }
//
//    /**
//     * 获取页数
//     *
//     * @param tableName
//     *            表名
//     * @param ddate
//     *            数据日期
//     * @param pageSize
//     *            分页大小
//     * @return 返回页数
//     */
//    public int getPages(String tableName, String ddate, int pageSize) throws Exception{
//        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(ddate)
//                || StringUtils.isBlank(pageSize + "")) {
//            System.out.println("===Parameters tableName|ddate|pageSize should not be blank,Please check!===");
//            return 0;
//        }
//        enableAggregation(tableName);
//        int total = 0;
//        try {
//            Table hTable = connection.getTable(TableName.valueOf(tableName));
//            Scan scan = new Scan();
//            Filter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL,
//                    new SubstringComparator(ddate));
//            scan.setFilter(rowFilter);
//            AggregationClient aggregation = new AggregationClient(HBaseUtil.getConfig());
//            Long count = aggregation.rowCount(hTable,
//                    new LongColumnInterpreter(), scan);
//            total = count.intValue();
//            hTable.close();
//        } catch (Throwable e) {
//            // TODO Auto-generated catch block
//            System.out.println(e);
//        }
//        return (total % pageSize == 0) ? total / pageSize
//                : (total / pageSize) + 1;
//    }
//
//    /**
//     * 根据发送状态获取页数
//     *
//     * @param tableName
//     *            表名
//     * @param ddate
//     *            数据日期
//     * @param pageSize
//     *            分页大小
//     * @param status
//     *            发送状态
//     * @return 返回页数
//     */
//    public int getPagesByStatus(String tableName, String ddate, int pageSize,
//                                String status) throws Exception{
//        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(ddate)
//                || StringUtils.isBlank(pageSize + "")
//                || StringUtils.isBlank(status)) {
//            System.out.println("===Parameters tableName|ddate|pageSize|status should not be blank,Please check!===");
//            return 0;
//        }
//        enableAggregation(tableName);
//        int total = 0;
//        try {
//            Table hTable = connection.getTable(TableName.valueOf(tableName));
//            Scan scan = new Scan();
//            FilterList filterList = new FilterList(
//                    FilterList.Operator.MUST_PASS_ALL);
//            Filter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL,
//                    new SubstringComparator(ddate));
//            filterList.addFilter(rowFilter);
//            filterList.addFilter(new SingleColumnValueFilter(Bytes
//                    .toBytes("info"), Bytes.toBytes("status"), CompareFilter.CompareOp.EQUAL,
//                    Bytes.toBytes(status)));
//            scan.setFilter(filterList);
//            AggregationClient aggregation = new AggregationClient(HBaseUtil.getConfig());
//            Long count = aggregation.rowCount(hTable,
//                    new LongColumnInterpreter(), scan);
//            total = count.intValue();
//            hTable.close();
//        } catch (Throwable e) {
//            // TODO Auto-generated catch block
//            System.out.println(e);
//        }
//        return (total % pageSize == 0) ? total / pageSize
//                : (total / pageSize) + 1;
//    }
//
//
//    /**
//     * 获取同一个rowkey下的记录集合
//     *
//     * @param result
//     *            结果集
//     * @return
//     */
//    private static List<Map<String, String>> getRowByResult1(Result result) {
//        if (result == null) {
//            System.out.println("===Parameter |result| should not be null,Please check!===");
//            return null;
//        }
//        List<Map<String, String>> cellMapList = new ArrayList<>();
//        for (Cell cell : result.listCells()) {
//            Map<String, String> cellMap = new HashMap<String, String>();
//            String tag = Bytes.toString(cell.getTagsArray(), cell.getTagsOffset(), cell.getTagsLength());
//            String rowkey = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
//            String cf = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
//            String qf = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
//            String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
//            cellMap.put("tag", tag);
//            cellMap.put("rowKey", rowkey);
//            cellMap.put("columnfamily", cf);
//            cellMap.put(qf, value);
//            cellMapList.add(cellMap);
//        }
//        return cellMapList;
//    }
//
//    /**
//     * 获取同一个rowkey下的记录集合
//     *
//     * @param result
//     *            结果集
//     * @return
//     */
//    private static Map<String, String> getRowByResult(Result result) {
//        if (result == null) {
//            System.out.println("===Parameter |result| should not be null,Please check!===");
//            return null;
//        }
//        Map<String, String> cellMap = new HashMap<String, String>();
//        for (Cell cell : result.listCells()) {
//            String rowkey = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
//            String columnfamily = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
//            String qf = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
//            String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
//            cellMap.put("rowKey", rowkey);
//            cellMap.put(columnfamily + ":" + qf, value);
//        }
//        return cellMap;
//    }
//
//
//    @SuppressWarnings("resource")
//    private void enableAggregation(String tableName) {
//        String coprocessorName = "org.apache.hadoop.hbase.coprocessor.AggregateImplementation";
//        try {
//            HBaseAdmin admin = new HBaseAdmin(HBaseUtil.getConfig());
//            HTableDescriptor htd = admin.getTableDescriptor(Bytes
//                    .toBytes(tableName));
//            List<String> coprocessors = htd.getCoprocessors();
//            if (coprocessors != null && coprocessors.size() > 0) {
//                return;
//            } else {
//                admin.disableTable(tableName);
//                htd.addCoprocessor(coprocessorName);
//                admin.modifyTable(tableName, htd);
//                admin.enableTable(tableName);
//            }
//        } catch (TableNotFoundException e) {
//            e.printStackTrace();
//        } catch (MasterNotRunningException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (ZooKeeperConnectionException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//
//}
//
//
