package cn.songo.springbootrabbitmqsongo.mapper;

import cn.songo.springbootrabbitmqsongo.bean.RabbitInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Date: Create in 10:16 2020/11/29
 * @Description:
 */
@Repository
@Mapper
public interface RabbitMapper {

    @Select("select msg_id msgId,exchange,routing_key routingKey,message,count,status,create_time createTime, modify_time modifyTime from mq_unsend_log where status = 0;")
    List<RabbitInfo> select();

    @Select("select msg_id msgId,exchange,routing_key routingKey,message,create_time createTime, modify_time modifyTime from mq_unsend_log where msg_id = #{msgId};")
    RabbitInfo get(String msgId);

    @Insert("insert into mq_unsend_log(msg_id,exchange,routing_key,message,create_time) values(#{msgId},#{exchange},#{routingKey},#{message},#{createTime});")
    Integer insert(RabbitInfo rabbitInfo);

    @Update("update mq_unsend_log set status = #{status}, modify_time = #{date} where msg_id = #{msgId};")
    void updateStatus(String msgId, int status, Date date);

    @Update("update mq_unsend_log set count = count+1, modify_time = #{date} where msg_id = #{msgId};")
    void updateCount(String msgId, Date date);
}
