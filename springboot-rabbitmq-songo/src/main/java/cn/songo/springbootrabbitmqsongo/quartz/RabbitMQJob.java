package cn.songo.springbootrabbitmqsongo.quartz;

import cn.songo.springbootrabbitmqsongo.bean.RabbitInfo;
import cn.songo.springbootrabbitmqsongo.config.AckRabbitTemplate;
import cn.songo.springbootrabbitmqsongo.service.RabbitService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class RabbitMQJob {


    @Autowired
    private RabbitService rabbitService;

    @Autowired
    private AckRabbitTemplate ackRabbitTemplate;

    /**
     * 10s 执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void doRabbitMQByJob() {
        log.info("doRabbitMQByJob start");

        //i、查询所有
        List<RabbitInfo> list = rabbitService.select();
        if (list == null || list.size() == 0) {
            log.info("没有记录");
            return;
        }

        //2、处理
        list.forEach(rabbitInfo -> {
            if (rabbitInfo.getCount() >= 3) {
                //重试 3 次后视为重发失败记录，并后续程序不予处理，需人工干预
                rabbitService.updateStatus(rabbitInfo.getMsgId(), 2);
            } else {
                //更新重发次数
                rabbitService.updateCount(rabbitInfo.getMsgId());
                ackRabbitTemplate.convertAndSend(rabbitInfo.getMsgId(), rabbitInfo.getExchange(), rabbitInfo.getRoutingKey(), rabbitInfo.getMessage());
            }
        });
        log.info("doRabbitMQByJob end");
    }
}
