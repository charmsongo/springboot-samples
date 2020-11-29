package cn.songo.springbootrabbitmqack0.controller;

import cn.songo.springbootrabbitmqack0.config.MessageSender;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: charmsongo
 * @Date: Create in 10:26 2020/11/16
 * @Description:
 */
@RestController
@Log4j2
public class TestRabbitMQ {
    @Autowired
    private MessageSender messageSender;


    @GetMapping("/hello")
    public void hello() {
        log.info("jinlai l ");
        messageSender.convertAndSend("test.exchange", "test.key", "test msg");
    }

}
