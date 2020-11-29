package cn.songo.springbootrabbitmqsongo.service;

import cn.songo.springbootrabbitmqsongo.bean.RabbitInfo;

import java.util.List;

/**
 * @Author: charmsongo
 * @Date: Create in 10:16 2020/11/29
 * @Description:
 */
public interface RabbitService {

    void saveToDB(String msgId, String exchange, String routingKey, String msg);

    List<RabbitInfo> select();

    void updateStatus(String msgId, int status);

    void updateCount(String msgId);

    RabbitInfo get(String msgId);
}
