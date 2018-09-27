package com.java.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.java.util.DateUtil;
import com.java.util.JacksonUtil;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.format.SignStyle;
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
