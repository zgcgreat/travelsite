package com.itcast.service.rabbitmq.tx;

import com.itcast.util.RabbitMqConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Joy
 * @version 1.0
 * @desc 事务
 * @date 2020/7/11 17:29
 */
public class Send {
    private static final String QUEUE_NAME = "queue_tx";

    public static void main(String[] args) {
        try {
            Connection connection = RabbitMqConnectionUtils.getConnection();
            Channel channel = connection.createChannel();
            //声明交换机
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //发送消息
            String msg = "hello tx msg...";
            try {
                channel.txSelect();
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
                channel.txCommit();
                System.out.println("Send:" + msg);
            } catch (Exception e) {
                channel.txRollback();
                System.out.println("send msg rollback");
            }

            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();

        }


    }
}
