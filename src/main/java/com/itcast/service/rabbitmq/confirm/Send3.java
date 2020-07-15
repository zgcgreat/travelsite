package com.itcast.service.rabbitmq.confirm;

import com.itcast.util.RabbitMqConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * @author Joy
 * @version 1.0
 * @desc confirm模式，异步
 * @date 2020/7/12 12:43
 */
public class Send3 {
    private static final String QUEUE_NAME = "queue_confirm_3";

    public static void main(String[] args) {
        try {
            Connection connection = RabbitMqConnectionUtils.getConnection();
            Channel channel = connection.createChannel();
            //声明交换机
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //设置为confirm模式
            channel.confirmSelect();
            //未确认的消息标识
            final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    if (multiple) {
                        System.out.println("handleAck----multiple");
                        confirmSet.headSet(deliveryTag + 1).clear();
                    } else {
                        System.out.println("handleAck---multiple false");
                        confirmSet.remove(deliveryTag);
                    }
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    if (multiple) {
                        System.out.println("handleNack----multiple");
                        confirmSet.headSet(deliveryTag + 1).clear();
                    } else {
                        System.out.println("handleNack---multiple false");
                        confirmSet.remove(deliveryTag);
                    }
                }
            });
            //发送消息
            String msg = "hello confirm batch msg...";
            while (true) {
                long seqNo = channel.getNextPublishSeqNo();
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
                confirmSet.add(seqNo);
            }

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();

        }
    }
}
