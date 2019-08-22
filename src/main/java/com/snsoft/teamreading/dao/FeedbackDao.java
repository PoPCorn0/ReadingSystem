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

import com.snsoft.teamreading.pojo.Feedback;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackDao {
    // 添加反馈
    @Insert("insert into feedback (id, author_id, content) values (#{id}, #{authorId}, #{content}")
    int addFeedback(Feedback feedback);
}
