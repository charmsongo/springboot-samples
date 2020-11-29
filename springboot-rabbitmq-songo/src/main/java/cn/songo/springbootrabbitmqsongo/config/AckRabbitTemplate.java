package cn.songo.springbootrabbitmqsongo.config;

import cn.songo.springbootrabbitmqsongo.service.RabbitService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @Author: charmsongo
 * @Date: Create in 14:30 2020/11/26
 * @Description: 消息发送确认
 * @Copyright Copyright (c) 2020, charmsongo@163.com All Rights Reserved.
 */
@Component
@Log4j2
public class AckRabbitTemplate implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitService rabbitService;

    /**
     * 初始化配置
     */
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
        // 要想使 returnCallback 生效，必须设置为true
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setEncoding(StandardCharsets.UTF_8.name());
    }


    /**
     * exchange 接收消息后确认信息
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String msgId = correlationData.getId();
        if (!(correlationData instanceof CorrelationDataExt)) {
            log.error("correlationData 对象不包含消息数据:[{}]", correlationData.toString());
            return;
        }
        CorrelationDataExt correlationDataExt = (CorrelationDataExt) correlationData;
        String msg = (String) correlationDataExt.getData();
        String routingKey = correlationDataExt.getRoutingKey();
        String exchange = correlationDataExt.getExchange();
        if (ack) {
            log.info("成功发送给 mq, msgId:[{}], msg:[{}] , routingKey:[{}], exchange:[{}]", msgId, msg, routingKey, exchange);
            if (rabbitService.get(msgId) != null) {
                rabbitService.updateStatus(msgId, 1);
            }
        } else {
            log.error("发送给 mq 失败, msgId:[{}], msg:[{}], routingKey:[{}], exchange:[{}], cause:[{}]", msgId, msg, routingKey, exchange, cause);
            //消息从生产者没有到 exchange，那存库
            rabbitService.saveToDB(msgId, exchange, routingKey, msg);
        }
    }

    /**
     * exchange 到 queue 失败回调
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        String msgId = (String) message.getMessageProperties().getHeaders().get("spring_returned_message_correlation");
        String msg = new String(message.getBody());
        log.error("消息回调 msgId:[{}], msg:[{}] 不能被正确路由，replyCode:[{}], replyText:[{}], exchange:[{}], routingKey:[{}]", msgId, msg, replyCode, replyText, exchange, routingKey);
        //消息从 exchange 没有到 queue， 那存库
        rabbitService.saveToDB(msgId, exchange, routingKey, msg);
    }

    /**
     * 生产者消息推送
     * @param exchange
     * @param routingKey
     * @param message
     */
    public void convertAndSend(String exchange, String routingKey, String message) {
        this.convertAndSend(UUID.randomUUID().toString(), exchange, routingKey, message);
    }

    /**
     * 生产者消息推送
     * @param msgId
     * @param exchange
     * @param routingKey
     * @param message
     */
    public void convertAndSend(String msgId, String exchange, String routingKey, String message) {

        CorrelationDataExt correlationData = new CorrelationDataExt();
        correlationData.setId(msgId);
        correlationData.setData(message);
        correlationData.setRoutingKey(routingKey);
        correlationData.setExchange(exchange);
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
        } catch (Exception e) {
            log.error("连接MQ失败:", e);
            //存储到db中进行重发
            rabbitService.saveToDB(msgId, exchange, routingKey, message);
        }
    }
}
