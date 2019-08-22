/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.08.22
 *
 * @Description
 */

package com.snsoft.teamreading.dao;

import com.snsoft.teamreading.pojo.Message;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MessageDao {

    // 根据id获取所有消息通知
    @Select("select id, target_id, content, send_time, is_read from message where target_id = #{id} " +
            "order by send_time DESC")
    List<Message> getMessages(String id, RowBounds rowBounds);

    // 发送一条新消息通知
    @Insert("insert into message (id, target_id, content) values(#{id}, #{targetId}, #{content})")
    int sendMessage(Message message);

    // 批量发送消息通知
    int sendMessages(ArrayList<Message> messages);

    // 删除一条消息通知
    @Delete("delete from message where id = #{id}")
    int deleteMessage(String id);
}
