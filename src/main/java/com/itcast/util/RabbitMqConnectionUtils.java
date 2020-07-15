package com.itcast.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

import java.util.concurrent.TimeoutException;

/**
 * @author Joy
 * @version 1.0
 * @desc 获取Mq的连接
 * @date 2020/7/11 10:26
 */
public class RabbitMqConnectionUtils {
    public static Connection getConnection() throws IOException, TimeoutException {
        //定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/vhost_mmr");
        factory.setUsername("user_mmr");
        factory.setPassword("123");
        return factory.newConnection();
    }
}
