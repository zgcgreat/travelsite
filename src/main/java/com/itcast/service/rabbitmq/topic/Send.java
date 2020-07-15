package com.itcast.service.rabbitmq.topic;

import com.itcast.util.RabbitMqConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Joy
 * @version 1.0
 * @desc 路由模式
 * @date 2020/7/11 17:29
 */
public class Send {
    private static final String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] args) {
        try {
            Connection connection = RabbitMqConnectionUtils.getConnection();
            Channel channel = connection.createChannel();
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            //发送消息
            String msg = "商品...";
            channel.basicPublish(EXCHANGE_NAME, "good.update", null, msg.getBytes());
            System.out.println("Send:" + msg);
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }


    }
}
