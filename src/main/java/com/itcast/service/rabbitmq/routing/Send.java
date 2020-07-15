package com.itcast.service.rabbitmq.routing;

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
    private static final String EXCHANGE_NAME = "exchange_direct";

    public static void main(String[] args) {
        try {
            Connection connection = RabbitMqConnectionUtils.getConnection();
            Channel channel = connection.createChannel();
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            //发送消息
            String msg = "hello direct!";
            String routingKey = "error";
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
            System.out.println("Send:" + msg);
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }


    }
}
