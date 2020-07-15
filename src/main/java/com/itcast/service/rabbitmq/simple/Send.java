package com.itcast.service.rabbitmq.simple;

import com.itcast.util.RabbitMqConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Joy
 * @version 1.0
 * @desc
 * @date 2020/7/11 12:58
 */
public class Send {
    private static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) {
        try {
            Connection connection = RabbitMqConnectionUtils.getConnection();
            Channel channel = connection.createChannel();
            //创建队列声明
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String msg = "hello world";
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            channel.close();
            connection.close();
            System.out.println("send msg:" + msg);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
