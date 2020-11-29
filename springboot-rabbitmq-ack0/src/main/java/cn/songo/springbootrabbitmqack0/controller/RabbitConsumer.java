package cn.songo.springbootrabbitmqack0.controller;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: charmsongo
 * @Date: Create in 10:21 2020/11/16
 * @Description:
 */
@Component
@Slf4j
public class RabbitConsumer {
    /**
     * 接收消息
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(
            bindings=@QueueBinding(
                    //配置交换器
                    exchange=@Exchange(value="test.exchange",type= ExchangeTypes.TOPIC),
                    //配置路由键
                    key="test.key",
                    //配置队列名称
                    value=@Queue(value="test.queue",autoDelete="true")
            )
    )
    public void test(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info(String.format("test 收到的消息为: {%s}", msg));
        try {
            //业务处理
            //int i = 1 / 1;
            //deliveryTag, multiple
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            e.printStackTrace();
        }
    }


}
