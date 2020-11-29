package cn.songo.springbootrabbitmqsongo.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
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
 * @Description: 消费者
 */
@Component
@Log4j2
public class TestConsumer {
    /**
     * 接收消息
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(
            bindings=@QueueBinding(
                    //配置交换器
                    exchange=@Exchange(value="${test.exchange}",type= ExchangeTypes.TOPIC),
                    //配置路由键
                    key="${test.one.binding-key}",
                    //配置队列名称
                    value=@Queue(value="${test.one.queue}",autoDelete="true")
            )
    )
    public void test(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("test 收到的消息为:[{}], msgId:[{}]", msg, message.getMessageProperties().getHeaders().get("spring_returned_message_correlation"));
        try {
            //业务处理
            //int i = 1 / 0;
            //deliveryTag, multiple
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
