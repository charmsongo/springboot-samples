package cn.songo.springbootrabbitmqsongo.publisher;

import cn.songo.springbootrabbitmqsongo.config.AckRabbitTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: charmsongo
 * @Date: Create in 10:26 2020/11/16
 * @Description:
 */
@RestController
@Log4j2
public class TestPublisher {
    @Autowired
    private AckRabbitTemplate ackRabbitTemplate;

    @Value("${test.exchange}")
    private String exchange;
    @Value("${test.one.binding-key}")
    private String routingKey;

    @GetMapping("/test")
    public void test() {
        log.info("coming.......");
        //ackRabbitTemplate.convertAndSend("test.exchange", "test.key11", "test msg");
        ackRabbitTemplate.convertAndSend(exchange, "routingKey", "test msg");
    }

}
