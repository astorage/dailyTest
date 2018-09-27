package com.java.mysql.service;

import com.java.mysql.dos.TCookiePushDO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Author: Boris
 * Date: 2018/9/5 23:06
 * Copyright (C), 2017-2018,
 * Description:
 */
@Data
@Service
@Scope("prototype")
public class InsertDataTask implements Runnable {

    private List<TCookiePushDO> dataList;

    private CountDownLatch latch;

    @Autowired
    private MysqlService mysqlService;

    @Override
    public void run() {
        mysqlService.insertData(dataList);
        latch.countDown();
        System.err.println("========" + latch.toString());
    }
}
