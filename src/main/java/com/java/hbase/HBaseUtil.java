/**
 * Copyright       (C), 2017-2018, 浙江执御信息技术有限公司
 * FileName:       HBaseUtil
 * Author:         Aubrey
 * Date:           2018/7/16 18:04
 * Description:    用户标签、地址标签hbase工具类
 */
package com.java.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.TableNotEnabledException;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HBaseUtil {

    //static final Logger LOG = LoggerFactory.getLogger(TagRiskServiceImpl.class);

    //private static TimeUtil timeUtil = new TimeUtil();

    private static Configuration config = HBaseConfiguration.create();
    private static Connection connection = null;

    static {
        if(connection == null){
            init();
        }
    }

    public static void init() {
        try {
            long start = System.currentTimeMillis();
            config.addResource("hbase-site.xml");
            String address = System.getProperty("hbase.zookeeper.quorum");
            if(address != null && address.trim().length() > 0){
                config.set("hbase.zookeeper.quorum",address);
            }
        	/*一个应用（进程）对应着一个connection，每个应用里的线程通过调用coonection的getTable方法从
            connection维护的线程池里获得table实例，按官方的说法，这种方式获得的table是线程安全的。
            每次table读写之后应该把table close掉，整个进程结束的时候才把connection close掉*/
            connection = ConnectionFactory.createConnection(config);
            long end = System.currentTimeMillis();
            //LOG.info("Invoke HBaseUtil init cost:" + (end - start));
        } catch (IOException e) {
            //LOG.error("创建HBase连接池失败!" + e.getMessage(), e);
        }
    }

    /**
     * 根据表名、键、列族、列查询HBase数据
     * @param tableName
     * @param rowKey
     * @param cf
     * @param column
     * @return
     * @throws Exception
     */
    public static Result get(String tableName, String rowKey, String cf, String column) throws Exception{
        Table table = null;

        long start = 0, end = 0;
        try {
            connection = getConnection();

            table = connection.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(cf), Bytes.toBytes(column));

            start = System.currentTimeMillis();
            Result result = table.get(get);

            return result;
        } catch (TableNotFoundException tableNotFoundException) {
            end = System.currentTimeMillis();
            String error = "获取HBase数据失败 表结构不存在 " + tableName + " get cost " +(end - start) + " " + tableNotFoundException.getMessage();
            //LOG.error(error, tableNotFoundException);
            throw tableNotFoundException;
        } catch (TableNotEnabledException tableNotEnabledException) {
            end = System.currentTimeMillis();
            String error = "获取HBase数据失败 表结构不可用 " + tableName + " get cost " +(end - start) + " " + tableNotEnabledException.getMessage();
            //LOG.error(error, tableNotEnabledException);
            throw tableNotEnabledException;
        } catch (Exception e) {
            end = System.currentTimeMillis();
            String error = "获取HBase数据失败 Exception异常 " + tableName + " get cost " +(end - start) + " " + e.getMessage();
            //LOG.error(error, e);
            throw e;
        } finally {
            try {
                if(table != null) {
                    table.close();
                }
            } catch (IOException e) {
                //LOG.error("关闭table实例失败!" + e.getMessage(), e);
            }
        }
    }

    /**
     * 获取连接池
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception{
        if (null == connection) {
            connection = ConnectionFactory.createConnection(config);
        }
        return connection;
    }

    public static void close(){
        if (null != connection) {
            try {
                //LOG.error("hbase connection start close");
                connection.close();
                //LOG.error("hbase connection start closed");
            } catch (Exception e) {
                //LOG.error("hbase connection close faild:", e);
            }
        }
    }

    public static Configuration getConfig() {
        return config;
    }
}
