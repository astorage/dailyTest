package com.java.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.java.util.DateUtil;
import com.java.util.JacksonUtil;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.format.SignStyle;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;

/**
 * Author: Boris
 * Date: 2018/8/2 14:55
 * Copyright (C), 2017-2018,
 * Description:
 */
public class TestBase {
    public static void main(String[] args) throws Exception {
        System.out.println((char)65);
        System.out.println(isWrapClass(char.class));
        System.out.println(isWrapClass(Long.class));
        System.out.println(isWrapClass(Integer.class));
        System.out.println(isWrapClass(String.class));
        System.out.println(isWrapClass(TestBase.class));
//
//        Map<String, String> map = new HashMap<>();
//        map.put("sss", "dddd");
//        map.put("sss1", "dddd1");
//        System.out.println(new ArrayList<>(map.values()));

//        System.out.println(getOrderIdByUUId());
//
//        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
//                .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
//                .appendValue(MONTH_OF_YEAR, 2)
//                .appendValue(DAY_OF_MONTH, 2)
//                .toFormatter();//ResolverStyle.STRICT, IsoChronology.INSTANCE
//        System.out.println(LocalDate.now().format(formatter));
//
//
//        List<String> list = new ArrayList<>();
//        list.add("push系统");
//        list.add("广告系统");
//        list.add("外呼系统");
//        list.add("responses系统");
//        list.add("客服系统");
//
//        List<String> list1 = list.stream().sorted().collect(Collectors.toList());
//        System.out.println(list1);
        //jsonTest();

        StringBuilder sb = new StringBuilder();
        sb.append("dskfjdkfj");
        sb.append("\n union \n");
        sb.append("sdfdf");
        System.out.println(sb.toString());



        Double dd = Math.round(0.34525652589491257 * 100) * 0.01d;
        System.out.println(dd);

        double d = 0.34425652589491257;
        String result = String.format("%1.2f", d);
        System.out.println("===" + result);
        double ddd = 1.342432;
        System.out.println(Math.ceil(ddd));

        /*Double d1 = null;
        if (d1 == 0 ){
            System.out.println("d1为空null");
        }else {
            System.out.println("d1：" + d1);
        }*/

        Long num = null;
//        if (num == 0) {
//            System.out.println("num 为0");
//        }

        List<Integer> nums = new ArrayList<>();
        nums.add(3);
        nums.add(5);
        nums.add(8);
        nums.add(23);
        nums.add(10);
       long count = nums.stream().mapToInt(Integer::intValue).sum();
        System.out.println(count);

        LocalDate today = LocalDate.now();
        System.out.println(today);
        System.out.println(today.format(DateUtil.FORMATTER));
        Double featureValue = 0.0;
        System.out.println(featureValue == (double)0);
        System.out.println(featureValue == 0);
        System.out.println(Double.valueOf("2.2289315E7"));

        String ddfdf = "dfdfdf";

        System.out.println(ddfdf.split(",")[0]);

        long ssdf = 0;
        System.out.println(ssdf);

        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list);
        list.add(1, 9);
        System.out.println(list);

        List<String> list1 = new ArrayList<>();
        list1.add("a");
        list1.add("b");
        list1.add("c");
        list1.add("d");
        list1.add("e");
        System.out.println(list1);
        list1.add(1, "z");
        System.out.println(list1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        System.out.println(sdf.parse("20181027"));

        System.out.println("sfdfd".equals(null));

        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now();
        LocalDateTime time1 = LocalDateTime.now();
        System.out.println(time1);

        long ll;
        //System.out.println(ll==0);

        LocalDate dd1d = LocalDate.now();

        System.out.println(dd1d);
        System.out.println(LocalDate.of(2018, 11, 9));
        System.out.println( dd1d.equals(LocalDate.of(2018, 11, 9)));


        String str = "GET /codalg?featureValueList=H4sIAAAAAAAAAOVXW44cNwy8ig9gLCS%2BeYCcIsj9r5EqUuMEcD6Sz8DrxmyvpiWSxapi%2B%2Fc8379dXPL9W36uM1f%2BtHLnOl9eip%2F9499cX%2F3zx%2F9k399L7P3BIlbjc%2B138dPKD7AM%2F%2FxXAQtcsc%2B1D9pPKz%2BQyWvSvwoy%2FR%2BQsfn5VZDRDCrqmh7W7OL8pUWLETnYKNf46HHaVN%2BmnMSzKEWx4lHXTMTmzl1deVfqaT53Ny0npGRbz7dhUgK%2BSshNqnuiny786hIe7jWfhhTSL%2B7DHBlUC1eK9xMZYTZyRG88nr13OPxMU6UiYwL7tSlRLk%2BwUJzmMfcd2IXd%2BGA6GixST%2BIEU%2BVK7rnXql%2FUuK22USu0N6rZFKlHIjZqqBTWgBOLyi6WfomuND61AII6cR6YrwQZCdzYmv4Ela0BpWZsqHAr%2BxR9Zk3toire5TlJ4J1fmOh0rAbt6Tgbzdp4gjKlvx5EEInMvVPRXWs%2FteHaTnX985qg2DMKOmEtZBdoMY2%2FB1UoGWSu01%2BTEyiyFIXjAaZM0nVpMsGyrrqbj9fLTGcQsOal60Iekw7OAUe304CZMS1MyTVPscspgZlwlF2P1GBsZ0qFOlh%2BgH42LVFHZDeURcMIMZ7CTDJ0c8KK1DbivI5AUPYy6dzOoVtDTW%2BkSdIr0EaXpb1ILnG9TERbfCSJPJE3KqeUSDFKFExJnnxxStVrTGynr%2BW9H0b4pll2R7nIRD1s1gLYEE65gJByFrSWEB070yfIhNyoM1yMOb6QNHajikKS7UYWJipeBUBaT%2Fs45a7uyrIfLveuAnVJkqrFhgNDDmZBKASMm2yVQxmcY0JUwnsMwpmRnCSRuMu4K5cB925S0xTNqbED%2FbqPmr69wFpGftYOe1ERTr2KWoKDuEHONitAui4pgIp4kp7SUQuaXeN%2F1umLIyKMVOkv8YgaYWtOCirJM6K7mSC12jtpy4UwtA57BtlAQWflP8%2BD6jmshNOG0SmJFyu56cEc4VlNcyyJGs3RB7kA2D3jvBZNPdOieiYJcq%2Bc8NXZtukBnMtYHj7xae1UAZzDqR29l2W6g7qrT6OErtNH7bSxjwo10hWt%2FJB4BpOUR1nzWhVZxOMpIuhDqVJWOzjwrnZwKJ2zE6jMNHLmCMU0VSuJKm1C0Ju1190LjsVGwWVidh2kwvWry40LUdhLBBa%2FXYo%2B%2BvHyJZNGeQ3V8uY9dJEo9gGCU7YB9dwxVhAiKBmf%2BLAY%2BikA4LMYX747Y3c%2ByurZ8UCYlzxN%2BS5PIbD%2BLJ1nv5uMfNmBV6T8xgy%2BDhlRnz8C%2FzuQ4B%2FtSIPducopMXYvHWOzILqlc6aeGv%2BvRotm1uLgx1Vo6gmaPNTH5Hy0wUbZZqHrC2DQAVg%2B1g5cYdJAC8BJo%2F0RX8IC0JzqxxQP5gVtDpooCy8WTQLBsGal%2Bh7WYAoJ9HloxRv%2B6ueNXHTh6Qvzb13vHlS7aekgD0pV1bgOTx0XwnvLCBl592g%2BatxWAfoZnUFYvmMEr0gcbwcQzisR%2FOK8iczH35TG%2FIo3CfIJzJ9hA%2BGIlTyHK1t5KpNfgszDZyCNlrBpcOYZfcj5NkHNzDOggdN8HYqjbGEDOnIawOmHTtafdwb74IJpuG5TGo%2FS2G47P3HwvNEhvzFiga2R5YHOsd0OjZCgaBSTzdvMQBRuzvHR3sxwdp%2B3%2Bw0He7wB9fy9RMDsl1XNx%2B5n7cNxA%2B3%2B%2BBP%2BCKeNGw8AAA%3D%3D HTTP/1.1[\\r][\\n]";
        String str2 = "{\"result\": 1, \"probability\": 0.6703, \"status\": 200}";
        System.out.println( str.getBytes().length + " : " + str2.getBytes().length);

        //0未同步 1 部分同步2 全部同步3 全部失败
        System.out.println(0&1);
        System.out.println(0|1);
        System.out.println(1|1);
        System.out.println(1&2);
        System.out.println(1|2);
        System.out.println(0&3);
        System.out.println(~0);
        System.out.println(~1);
        System.out.println(~2);
        System.out.println(~3);
        System.out.println("dfdf");

        for (int i =1 ; i < 2; i++ ) {
            System.out.println(i);
        }

        System.out.println((int)4444l);
        System.out.println("3.0".contains(".") ? Double.valueOf("3") : Long.valueOf("3"));

        String star = "3";
        if (star.contains(".")) {
            System.out.println(Double.valueOf(star));
        }else {
            System.out.println(Long.valueOf(star));
        }

        String ss = "yyyy-MM-dd hh:mm:ss";
        System.out.println(ss.length());

        String sdfd = "155BB442-41F2-41B5-BE3F-D5B4A6F1D95F6D39E045-68CD-467E-875D-A167EADF35D7";
        System.out.println(sdfd.getBytes().length);
        System.out.println(sdfd.length());

        Boolean b1 = true;
        Object obj = b1;
        Boolean b2 = true;
        if (b2.equals(obj)) {
            System.out.println("sdfdsfdsf");
        }

        Set<String> set1 = new HashSet<>();
        set1.add("3");
        set1.remove("3");
        System.out.println(set1.isEmpty());

        System.out.println(Integer.valueOf("43 34"));

    }



    public static boolean isWrapClass(Class clz) {
        try {
            //clz.isPrimitive()

            return ((Class) clz.getField("TYPE").get(null)).isPrimitive()||clz.isPrimitive();

        } catch (Exception e) {
            return false;
        }
    }

    public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return machineId + String.format("%015d", hashCodeV);
    }

    public static void jsonTest() {
        List<List<String>> resultStr = JacksonUtil.deserializeIgnoreException("[[\"'1df'\", \"'2df'\", \"'3dfd'\"], [\"'4dfd'\"], [\"'5dfd'\", \"'6dfd'\", \"'7fdf'\"]]", List.class);
        System.out.println(resultStr);

    }


    public static void page(){

        StringBuilder sql = new StringBuilder("select ");
        int min = 0;
        int max = 564;
        int pageSize = 1;
        int page = 1;
        int end;
        int start;

        do {
            start = (page-1)*pageSize + min;
            end = page*pageSize + min;
            sql.append("COUNT(CASE WHEN tagweight>=").append(start).append(" and tagweight <").append(end).append(" THEN tagweight END) AS '").append(start).append("-").append(end).append("'");
            sql.append(", ");
            System.out.println(start + "-" + end);
            page++;
        }while(end < max);
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append(" from  zybiro.test_test where tagid='B220H039_0_001'");
        System.out.println(sql.toString());
    }

    /**
     * 加载算法特征配置文件
     * @param name
     * @throws IOException
     */
    private static Map<String, Object> loadData(String name) throws IOException {
        InputStream inputStream = TestBase.class.getResourceAsStream(name);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line =   br.readLine();
        while (line != null){
            sb.append(line);
            line =   br.readLine();
        }
        Map<String, Object> dataMap = JSONObject.parseObject(sb.toString(), Map.class);
        return dataMap;
    }

    /**
     * 加载算法特征配置文件
     * @param name
     * @throws IOException
     */
    private static Map<String, Object> loadData1(String name) throws IOException {
        URL url = TestBase.class.getResource(name);
        String path = url.getPath();
        File file = new File(path);
        String conent = FileUtils.readFileToString(file, "UTF-8");
        Map<String, Object> dataMap = JSONObject.parseObject(conent, Map.class);
        return dataMap;
    }


}
