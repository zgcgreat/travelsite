package com.itcast.service.rabbitmq.ps;

import com.itcast.util.RabbitMqConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

/**
 * @author Joy
 * @version 1.0
 * @desc
 * @date 2020/7/11 16:42
 */
public class Recv1 {
    private static final String QUEUE_NAME = "queue_fanout_email";
    private static final String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = RabbitMqConnectionUtils.getConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //绑定队列到交换机
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");

            //公平分发
            channel.basicQos(1);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String msg = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [1] Received '" + msg + "'");
                try {
                    Thread.sleep(1000);
                    //手动回执
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            //自动应答
            boolean autoAck = false;
            channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
