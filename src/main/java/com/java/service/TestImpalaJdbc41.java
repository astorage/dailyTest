package com.java.service;

import java.math.BigDecimal;
import java.sql.*;

/**
 * Author: Boris
 * Date: 2018/8/1 11:53
 * Copyright (C), 2017-2018,
 * Description:
 */
public class TestImpalaJdbc41 {
    static String JDBCDriver = "com.cloudera.impala.jdbc41.Driver";
    //static String JDBCDriver = "org.apache.hive.jdbc.HiveDriver";
    // Define a string as the connection URL
    //static String ConnectionURL = "jdbc:impala://192.168.1.1:21050";10.155.74.234 10.155.74.234
    static String ConnectionURL = "jdbc:impala://169.44.92.20:25000/zydb;AuthMech=3;UID=zybi;PWD=zybi2013db;";
    //static String ConnectionURL = "jdbc:hive2://10.155.74.234:21050/;auth=noSasl";

    public static void main(String[] args) {
        queryImpala();
    }

    public static String queryImpala () {

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        // Define the SQL statement for a query
        String query = "select * from dw.dw_profile_tag_user_userid where data_date='20180731' limit 4 ";

        try {

            // Register the driver using the class name
            Class.forName(JDBCDriver);

            // Establish a connection using the connection URL
            con = DriverManager.getConnection(ConnectionURL);

            // Create a Statement object for sending SQL
            // statements to the database
            stmt = con.createStatement();


            // Execute the SQL statement
            rs = stmt.executeQuery(query);

            // Display a header line for output appearing in
            // the Console View
            System.out.printf("%20s%20s\r\n", "STORE SALES",
                    "STORE COST");

            // Step through each row in the result set returned
            // from the database
            rs.next();
            Integer StoreSales = rs.getInt(0);
//            while() {
//                // Retrieve values from the row where the
//                // cursor is currently positioned using column
//                // names
//
//
////                BigDecimal StoreCost = rs.getBigDecimal
////                        ("store_cost");
//
//                // Display values in columns 20 characters
//                // wide in the Console View using the
//                // Formatter
//                System.out.printf("%20s%20s\r\n",
//                        StoreSales.toString());
//            }
        return StoreSales.toString();
        } catch (SQLException se) {
            // Handle errors encountered during interaction
            // with the data source
            se.printStackTrace();
            return se.getMessage();
        } catch (Exception e) {
            // Handle other errors
            e.printStackTrace();
            return e.getMessage();
        } finally {
            // Perform clean up
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException se1) {
                // Log this
            }

            try {
                if (stmt!=null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                // Log this
            }

            try {
                if (con!=null) {
                    con.close();
                }
            } catch (SQLException se3) {
                // Log this
                se3.printStackTrace();
            } // End try

        } // End try

    } // End main
} // End ClouderaJDBCImpalaExample



