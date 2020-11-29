package cn.songo.springbootrabbitmqack0.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Date: Create in 14:55 2020/11/25
 * @Description: 消息发送
 */
@Component
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void convertAndSend(String exchange, String routingKey, String message) {


        String msgId = UUID.randomUUID().toString();
        CorrelationDataExt0 correlationData = new CorrelationDataExt0();
        correlationData.setId(msgId);
        correlationData.setData(message);
        correlationData.setRoutingKey(routingKey);
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
    }
}
