package com.java.mysql;

import com.java.mysql.dos.TCookiePushDO;
import com.java.mysql.service.InsertDataTask;
import com.java.mysql.service.MysqlService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * Author: Boris
 * Date: 2018/9/5 16:35
 * Copyright (C), 2017-2018,
 * Description:
 */

public class MysqlTest {

    private MysqlService mysqlService;

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private ApplicationContext context;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
        MysqlService mysqlService =  (MysqlService)context.getBean("mysqlServiceImpl");
        long start = System.currentTimeMillis();
        Map<String, Object> param = new HashMap<>();
        param.put("offset", 0);
        param.put("pageSize", 500);
        param.put("categoryId", 415590);
        List<TCookiePushDO> tCookiePushDOList = mysqlService.pageQueryByCategoryId(param);
        long end = System.currentTimeMillis();
        System.out.println(""+ (end - start) + "毫秒");
        System.out.println(tCookiePushDOList);

        System.out.println(dateTOString(new Date()));
    }

    @Before
    public void before() {
        context = new ClassPathXmlApplicationContext("classpath:application.xml");
        mysqlService =  (MysqlService)context.getBean("mysqlServiceImpl");
        threadPoolTaskExecutor = (ThreadPoolTaskExecutor)context.getBean("threadPoolTaskExecutor");

    }

    @Test
    public void insertBatchDataTest() {
        CountDownLatch latch = new CountDownLatch(300000);
        // String todayStr = dateTOString(new Date());
        for (int m = 0; m < 30; m ++){
            long categoryId = Math.round(1000000*Math.random());
            for (int j = 0; j < 100; j ++){
                //long size = Math.round(5000*Math.random());
                for (int k = 0; k<100; k++) {
                    List<TCookiePushDO> dataList = new ArrayList<>();
                    for (int i = 0; i < 100; i ++){
                        TCookiePushDO tCookiePushDO = new TCookiePushDO();
                        tCookiePushDO.setCategoryId(Integer.valueOf(String.valueOf(categoryId)));
                        tCookiePushDO.setDataDate("20181024");
                        String cookieId = UUID.randomUUID().toString();
                        tCookiePushDO.setUser(cookieId);
                        tCookiePushDO.setSequNum((j*10000 +k*100 + i + 1));
                        dataList.add(tCookiePushDO);
                    }
                    InsertDataTask insertDataTask = context.getBean("insertDataTask", InsertDataTask.class);
                    insertDataTask.setDataList(dataList);
                    insertDataTask.setLatch(latch);
                    threadPoolTaskExecutor.submit(insertDataTask);
                }

            }
        }

        try {
            latch.await();
            System.out.println("Main finished");
            threadPoolTaskExecutor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static String dateTOString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        return sdf.format(date);
    }

    @Test
    public void insertDataTest() {
        int num = 1000;
        CountDownLatch latch = new CountDownLatch(num);
        long categoryId = Math.round(1000000*Math.random());
        System.out.println(categoryId);
        String todayStr = dateTOString(new Date());
        List<TCookiePushDO> dataList = new ArrayList<>();
        for (int j=0; j<num; j++) {
            for (int i = 0; i < 5000; i++) {
                TCookiePushDO tCookiePushDO = new TCookiePushDO();
                tCookiePushDO.setCategoryId(Integer.valueOf(String.valueOf(categoryId)));
                String cookieId = UUID.randomUUID().toString();
                dataList.add(tCookiePushDO);
            }

            //mysqlService.insertData(dataList);
            InsertDataTask insertDataTask = context.getBean("insertDataTask", InsertDataTask.class);
            insertDataTask.setDataList(dataList);
            insertDataTask.setLatch(latch);
            System.err.println(j);
            threadPoolTaskExecutor.submit(insertDataTask);
        }

        try {
            System.out.println("等待子线程结束~~~");
            latch.await();
            System.out.println("Main finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void insertBatchDataTest1() {
        long categoryId = Math.round(1000000*Math.random());

        for (int k = 0; k<100; k++) {
            List<TCookiePushDO> dataList = new ArrayList<>();
            for (int i = 0; i < 100; i ++){
                TCookiePushDO tCookiePushDO = new TCookiePushDO();
                tCookiePushDO.setCategoryId(Integer.valueOf(String.valueOf(categoryId)));
                tCookiePushDO.setDataDate("20181022");
                String cookieId = UUID.randomUUID().toString();
                tCookiePushDO.setUser(cookieId);
                tCookiePushDO.setSequNum((k*100 + i + 1));
                dataList.add(tCookiePushDO);
            }
            mysqlService.insertData(dataList);
        }
    }
}
