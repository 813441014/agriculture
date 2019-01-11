package com.qpp.db.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName RedisReceiver
 * @Description TODO 消息接收者（订阅者）
 * @Author qipengpai
 * @Date 2018/12/7 16:16
 * @Version 1.0.1
 */
@Slf4j
@Component
public class RedisReceiver {

    private CountDownLatch latch;

    @Autowired
    public RedisReceiver(CountDownLatch latch) {
        this.latch = latch;
    }


    /**
     *  收到通道的消息之后执行的方法
     * @param message
     */
    public void receiveMessage(String message) {
        //这里是收到通道的消息之后执行的方法
        log.info("我收到通道里你发的的消息了....." + message);

        latch.countDown();
    }
}
