package cn.songo.springbootrabbitmqack0.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: charmsongo
 * @Date: Create in 15:14 2020/11/25
 * @Description: 当消息不能被正确路由到某个 queue 时，会回调如下方法
 */
@Component
@Log4j2
public class ReturnCallback implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        //System.out.println(message.getMessageProperties().getCorrelationId());
        String msg = new String(message.getBody());
        log.error(String.format("消息 {%s} 不能被正确路由，replyCode为 {%s}, replyText为 {%s}, routingKey为 {%s}", msg, replyCode, replyText, routingKey));
        //todo 消息从 exchange 没有到 queue， 那存库？？？？
    }
}
