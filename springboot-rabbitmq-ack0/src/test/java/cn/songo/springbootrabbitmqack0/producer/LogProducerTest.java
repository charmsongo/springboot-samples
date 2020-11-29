package cn.songo.springbootrabbitmqack0.producer;

import cn.songo.springbootrabbitmqack0.config.MessageSender;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: charmsongo
 * @Date: Create in 15:34 2020/11/25
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogProducerTest {

    @Autowired
    private MessageSender messageSender;
    @Value("${log.exchange}")
    private String exchange;
    @Value("${log.info.binding-key}")
    private String routingKey;

    /**
     * 测试失败通知
     */
    @SneakyThrows
    @Test
    public void sendErrorMsg() {
        for (int i = 0; i < 3; i++) {
            String message = "this is error message " + i;
            try{
                messageSender.convertAndSend(exchange, "test", message);
            }catch (Exception e){
                System.out.println("连接MQ失败"+ e);
                //todo 存储到db中进行重发
            }
        }
        System.in.read();
    }

    /**
     * 测试发布者确认
     */
    @SneakyThrows
    @Test
    public void sendInfoMsg() {
        for (int i = 0; i < 3; i++) {
            String message = "this is info message " + i;
            try{
                messageSender.convertAndSend(exchange, routingKey, message);
            }catch (Exception e){
                System.out.println("连接MQ失败"+ e);
                //todo 存储到db中进行重发
            }
        }
        System.in.read();
    }

}
