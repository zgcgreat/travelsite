package com.itcast.service.rabbitmq.confirm;

import com.itcast.util.RabbitMqConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Joy
 * @version 1.0
 * @desc confirm模式，批量发送
 * @date 2020/7/12 12:43
 */
public class Send2 {
    private static final String QUEUE_NAME = "queue_confirm_2";

    public static void main(String[] args) {
        try {
            Connection connection = RabbitMqConnectionUtils.getConnection();
            Channel channel = connection.createChannel();
            //声明交换机
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //设置为confirm模式
            channel.confirmSelect();
            //发送消息
            String msg = "hello confirm batch msg...";
            for (int i = 0; i < 10; i++) {
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            }
            if(!channel.waitForConfirms()){
                System.out.println("msg send failed");
            }else {
                System.out.println("msg send ok");
            }

            channel.close();
            connection.close();
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();

        }
    }
}
