package cn.songo.springbootrabbitmqsongo.bean;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: charmsongo
 * @Date: Create in 10:42 2020/11/29
 * @Description:
 */
@Data
@ToString
public class RabbitInfo {

    private String msgId;
    private String routingKey;
    private String exchange;
    private String message;
    private Integer count;
    private Integer status;
    private Date createTime;
    private Date modifyTime;

}
