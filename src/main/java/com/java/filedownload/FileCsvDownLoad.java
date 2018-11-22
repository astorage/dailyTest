package com.java.filedownload;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionInfo;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SCPInputStream;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Boris
 * Date: 2018/11/21 13:38
 * Copyright (C), 2017-2018, 浙江执御信息技术有限公司
 * Description:
 */
public class FileCsvDownLoad {

    public static File createCSVFile(List<Object> headList, List<Object> dataList, String outputPath, String fileName) {

        File file = null;
        BufferedWriter writer = null;
        file = new File(outputPath + File.separator + fileName + ".csv");
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            System.out.println("目录不存在创建");
            parent.mkdir();
        }

        try {
            file.createNewFile();
            System.out.println("csvFile:" + file);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GB2312"), 1024);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    private static final byte[] UTF_BOM = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
    private static final String[] header = new String[]{"cookieid"};

    public static void doExport(HttpServletResponse response) throws Exception {
        OutputStream outputStream = null;
        response.setContentType(ExtensionMode.CSV.getContentType() + ";charset=UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename="
                + URLEncoder.encode(getFileName(ExtensionMode.CSV.getSuffix()), "UTF-8"));
        outputStream = response.getOutputStream();
        List<String[]> dataList = new ArrayList<>();
        String[] cookie1 = {"0000cd13-10c7-4f5e-a3ea-9c8df445f7d0"};
        dataList.add(cookie1);
        String[] cookie2 = {"00012673-8a86-4055-bd0c-5485bde33918"};
        dataList.add(cookie2);
        String[] cookie3 = {"00014af4-273c-4777-9097-2000299263af"};
        dataList.add(cookie3);
        String[] cookie4 = {"00016117-9730-4830-b537-f1e7d5e315e6"};
        dataList.add(cookie4);
        String[] cookie5 = {"0002c755-a62d-4282-bce6-a8f003e91276"};
        dataList.add(cookie5);

        try {
            export(ExtensionMode.CSV, header, dataList, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static void export (ExtensionMode extensionMode, String[]header, List<String[]> lines,
                                OutputStream outputStream) throws IOException {
        if (extensionMode == ExtensionMode.CSV) {
            outputStream.write(UTF_BOM);  //防止中文乱码
        }
        extensionMode.write(lines, header, outputStream);
        outputStream.flush();
    }

    private static String getFileName (String extension){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date()) + extension;
    }


    public static void downloadFile(HttpServletResponse response) throws Exception{
        OutputStream outputStream = null;
        response.setContentType(ExtensionMode.CSV.getContentType() + ";charset=UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename="
                + URLEncoder.encode(getFileName(ExtensionMode.CSV.getSuffix()), "UTF-8"));
        outputStream = response.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));

        File file = new File("D:\\20181121165432.csv");
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
        String line;
        while (StringUtils.isNotEmpty(line = br.readLine())) {
            bw.write(line);
            bw.newLine();
        }
        bw.flush();
    }

    public static void downloadRemoteFile(HttpServletResponse response) throws Exception{
        OutputStream outputStream = null;
        response.setContentType(ExtensionMode.CSV.getContentType() + ";charset=UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename="
                + URLEncoder.encode(getFileName(ExtensionMode.CSV.getSuffix()), "UTF-8"));
        outputStream = response.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));

        File file = new File("////172.31.2.215//home/boris/test/20181121165432.csv");
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
        String line;
        while (StringUtils.isNotEmpty(line = br.readLine())) {
            bw.write(line);
            bw.newLine();
        }
        bw.flush();
    }


    public static void downloadRemoteLinuxFile(HttpServletResponse response) throws Exception{
        OutputStream outputStream = null;
        response.setContentType(ExtensionMode.CSV.getContentType() + ";charset=UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename="
                + URLEncoder.encode(getFileName(ExtensionMode.CSV.getSuffix()), "UTF-8"));
        outputStream = response.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));

        Connection connection = new Connection("172.31.2.215");
        ConnectionInfo connect = connection.connect();
        boolean isAuthed = connection.authenticateWithPassword("boris", "yubo1234_");
        SCPClient scpClient = connection.createSCPClient();
        SCPInputStream scpInputStream = scpClient.get("/home/boris/test/20181121165432.csv");


        BufferedReader br = new BufferedReader(new InputStreamReader(scpInputStream));
        String line;
        while (StringUtils.isNotEmpty(line = br.readLine())) {
            bw.write(line);
            bw.newLine();
        }
        bw.flush();

        if (outputStream != null ) {
            outputStream.close();
        }
        if (bw != null) {
            bw.close();
        }
//        if (br != null) {
//            br.close();
//        }
        if (scpInputStream != null) {
            scpInputStream.close();
        }

        if (connection != null) {
            connection.close();
        }


    }






}
