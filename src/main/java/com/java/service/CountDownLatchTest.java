package com.java.service;

import org.apache.hbase.thirdparty.io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author: Boris
 * Date: 2018/9/18 15:52
 * Copyright (C), 2017-2018,
 * Description:
 */
public class CountDownLatchTest {
    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(100,
            200, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
            new DefaultThreadFactory("cod-pool"), new ThreadPoolExecutor.DiscardOldestPolicy());
    public static void main(String[] args) throws Exception{
        //CountDownLatch countDown = new CountDownLatch(1);
        CountDownLatch await = new CountDownLatch(5);

        // 依次创建并启动处于等待状态的5个MyRunnable线程
        for (int i = 0; i < 5; ++i) {
            pool.submit(new MyRunnable1(await));
        }

//        System.out.println("用于触发处于等待状态的线程开始工作......");
//        System.out.println("用于触发处于等待状态的线程工作完成，等待状态线程开始工作......");
        //countDown.countDown();
        await.await();
        System.out.println("Bingo!");
    }
}


class MyRunnable1 implements Runnable {

    private final CountDownLatch await;

    public MyRunnable1(CountDownLatch await) {
        this.await = await;
    }

    public void run() {
        System.out.println("处于等待的线程开始自己预期工作......");
        await.countDown();//完成预期工作，发出完成信号...

    }
}

class MyRunnable implements Runnable {

    private final CountDownLatch countDown;
    private final CountDownLatch await;

    public MyRunnable(CountDownLatch countDown, CountDownLatch await) {
        this.countDown = countDown;
        this.await = await;
    }

    public void run() {
        try {
            countDown.await();//等待主线程执行完毕，获得开始执行信号...
            System.out.println("处于等待的线程开始自己预期工作......");
            await.countDown();//完成预期工作，发出完成信号...
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


