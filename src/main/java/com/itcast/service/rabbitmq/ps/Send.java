package com.itcast.service.rabbitmq.ps;

import com.itcast.util.RabbitMqConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Joy
 * @version 1.0
 * @desc 订阅模式
 * @date 2020/7/11 17:29
 */
public class Send {
    private static final String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] args) {
        try {
            Connection connection = RabbitMqConnectionUtils.getConnection();
            Channel channel = connection.createChannel();
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            //发送消息
            String msg = "hello ps";
            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
            System.out.println("Send:" + msg);
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }


    }
}
