package cn.songo.springbootrabbitmqack0.consumer;

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
 * @Date: Create in 15:23 2020/11/25
 * @Description:
 */
@Component
@Log4j2
public class LogListenerManual0 {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${log.info.queue}", autoDelete = "true"),
                    exchange = @Exchange(value = "${log.exchange}", type = ExchangeTypes.TOPIC),
                    key = "${log.info.binding-key}"
            )
    )
    public void infoLog(Message message, Channel channel) throws Exception {

        String msg = new String(message.getBody());
        log.info(String.format("infoLogQueue 收到的消息为: {%s}", msg));
        try {
            //业务处理
            int i = 1 / 1;
            //deliveryTag, multiple
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            e.printStackTrace();
            //todo 入库？？？
        }

    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${log.all.queue}", autoDelete = "true"),
                    exchange = @Exchange(value = "${log.exchange}",type = ExchangeTypes.TOPIC),
                    key = "${log.all.binding-key}"
            )
    )
    public void allLog(Message message, Channel channel) throws Exception {
        String msg = new String(message.getBody());
        log.info(String.format("allLog 收到的消息为: {%s}", msg));
        try {
            //业务处理
            //deliveryTag, multiple
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            e.printStackTrace();
            //todo 入库？？？
        }

    }
}

