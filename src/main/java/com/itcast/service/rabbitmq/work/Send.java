package com.itcast.service.rabbitmq.work;

import com.itcast.util.RabbitMqConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Joy
 * @version 1.0
 * @desc 工作队列
 * @date 2020/7/11 16:30
 */
public class Send {
    private static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) {
        try {
            Connection connection = RabbitMqConnectionUtils.getConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            int prefetchCount = 1;
            //公平分发
            channel.basicQos(prefetchCount);
            for (int i = 0; i < 50; i++) {
                String msg = "[" + i + "]Send msg: hello worker";
                System.out.println(msg);
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

            }
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
