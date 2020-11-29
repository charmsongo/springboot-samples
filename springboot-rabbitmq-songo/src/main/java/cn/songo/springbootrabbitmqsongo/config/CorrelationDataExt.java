package cn.songo.springbootrabbitmqsongo.config;

import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * @Author: charmsongo
 * @Date: Create in 16:34 2020/11/25
 * @Description: CorrelationData的自定义实现，用于拿到消息内容
 */
public class CorrelationDataExt extends CorrelationData {
    //数据
    private volatile Object data;
    //路由键
    private String routingKey;
    //交换机
    private String exchange;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}
