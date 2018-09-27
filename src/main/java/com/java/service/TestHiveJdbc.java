package com.java.service;

import org.junit.Before;
import org.junit.Test;

import java.sql.*;

/**
 * Author: Boris
 * Date: 2018/8/1 14:19
 * Copyright (C), 2017-2018,
 * Description:
 */
public class TestHiveJdbc {
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    //创建连接
    @Before
    public void getConnection() {
        try {

            Class.forName("org.apache.hive.jdbc.HiveDriver");
//            Class.forName("com.cloudera.impala.jdbc41.Driver");
            connection = DriverManager.getConnection("jdbc:hive2://169.44.92.20:25000/zydb", "zybi", "zybi2013db");
            System.out.println(connection);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //关闭连接
    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void find() throws SQLException {
        String sql = "select * from dw.dw_profile_tag_user_userid where data_date='20180731' limit 4 ";
        ps = connection.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getObject(1) + "---" + rs.getObject(2));
        }
        close();
    }
    public static void main(String[] args) {

    }
}
