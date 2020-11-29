package cn.songo.springbootrabbitmqsongo.service;

import cn.songo.springbootrabbitmqsongo.bean.RabbitInfo;
import cn.songo.springbootrabbitmqsongo.mapper.RabbitMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: charmsongo
 * @Date: Create in 10:16 2020/11/29
 * @Description:
 */
@Service
@Log4j2
public class RabbitServiceImpl implements RabbitService{

    @Autowired
    private RabbitMapper rabbitMapper;

    @Override
    public void saveToDB(String msgId, String exchange, String routingKey, String msg) {
        RabbitInfo rabbitInfo = rabbitMapper.get(msgId);
        if (rabbitInfo != null) {
            log.info("该记录已存在");
            return;
        }
        rabbitInfo = new RabbitInfo();
        rabbitInfo.setMsgId(msgId);
        rabbitInfo.setExchange(exchange);
        rabbitInfo.setRoutingKey(routingKey);
        rabbitInfo.setMessage(msg);
        rabbitInfo.setCreateTime(new Date());
        rabbitMapper.insert(rabbitInfo);
    }

    @Override
    public List<RabbitInfo> select() {
        return rabbitMapper.select();
    }

    @Override
    public void updateStatus(String msgId, int status) {
        rabbitMapper.updateStatus(msgId, status, new Date());
    }

    @Override
    public void updateCount(String msgId) {
        rabbitMapper.updateCount(msgId,new Date());
    }

    @Override
    public RabbitInfo get(String msgId) {
        return rabbitMapper.get(msgId);
    }
}
