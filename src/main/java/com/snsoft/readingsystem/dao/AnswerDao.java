/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.19
 *
 * @Description
 */

package com.snsoft.readingsystem.dao;

import com.snsoft.readingsystem.pojo.Answer;
import com.snsoft.readingsystem.returnPojo.AnswerInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerDao {

    // 根据id查询解读
    @Select("select id, task_id, received_task_id, author_id, tier, title, content, commit_time, praise_amount " +
            "from answer " +
            "where id = #{id}")
    Answer getAnswerById(@Param("id") String answerId);

    // 根据导师id获取其创建的所有团队的解读
    List<AnswerInfo> getTeacherAnswerInfo(String teacherId, RowBounds rowBounds);

    // 根据学生id获取其所在的所有团队的解读
    List<AnswerInfo> getStudentAnswerInfo(String studentId, RowBounds rowBounds);

    // 根据已接受任务id查看解读及其下所有追加解读
    List<AnswerInfo> getAnswerInfosByReceivedTaskId(String receivedTaskId);
}
