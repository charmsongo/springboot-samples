package cn.songo.springbootrabbitmqack0.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: charmsongo
 * @Date: Create in 14:52 2020/11/25
 * @Description: 用来判断消息是否被 ack
 */
@Component
@Log4j2
public class ConfirmCallback implements RabbitTemplate.ConfirmCallback {

    /**
     *
     * @param correlationData
     * @param ack
     * @param cause 发送失败的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String msgId = correlationData.getId();
        if (!(correlationData instanceof CorrelationDataExt0)) {
            log.error("correlationData对象不包含数据");
            //todo 其他处理
        }
        CorrelationDataExt0 correlationDataExt0 = (CorrelationDataExt0) correlationData;
        String msg = (String) correlationDataExt0.getData();
        //String routingKe = correlationDataExt0.getRoutingKey();

        if (ack) {
            log.info(String.format("消息 {%s} 成功发送给mq, msgId:{%s}", msg, msgId));
        } else {
            log.error(String.format("消息 {%s} 发送给mq失败, msgId:{%s},原因:{%s}", msg, msgId, cause));
            //todo 消息从生产者没有到 exchange，那存库？？？
        }

    }
}
