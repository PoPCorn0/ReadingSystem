/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.15
 *
 * @Description
 */

package com.snsoft.readingsystem.dao;

import com.snsoft.readingsystem.pojo.Feedback;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackDao {
    // 添加反馈
    @Insert("insert into feedback (id, author_id, content) values (#{id}, #{authorId}, #{content}")
    public int addFeedback(Feedback feedback);
}
