/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.13
 *
 * @Description
 */

package com.snsoft.readingsystem.dao;

import com.snsoft.readingsystem.pojo.PendingAnswer;
import com.snsoft.readingsystem.returnPojo.PendingAnswerInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PendingAnswerDao {

    //根据已接受任务id查询待审核解读
    @Select("select " +
            "id, received_task_id, author_id, title, content, commit_time, check_time, check_mark, reason" +
            " from pending_answer " +
            "where received_task_id = #{receivedTaskId}")
    public PendingAnswer getPendingAnswerByReceivedTaskId(@Param("receivedTaskId") String receivedTaskId);

    // 添加待审核解读
    @Insert("insert into pending_answer " +
            "(id, received_task_id, author_id, title, content) " +
            "values (#{id}, #{receivedTaskId}, #{authorId}, #{title}, #{content})")
    public int addPendingAnswer(PendingAnswer pendingAnswer);

    // 根据id查询待审核解读
    @Select("select " +
            "id, received_task_id, author_id, title, content, commit_time, check_time, check_mark, reason " +
            "from pending_answer " +
            "where id = #{id}")
    public PendingAnswer getPendingAnswerById(@Param("id") String id);

    // 根据id删除待审核解读
    @Delete("delete from pending_answer where id = #{id}")
    public int deletePendingAnswer(@Param("id") String id);

    // 根据用户id查询已通过审核的解读
    public List<PendingAnswerInfo> getApprovedAnswers(String studentId, RowBounds rowBounds);

    // 根据用户id查询未通过审核的解读
    public List<PendingAnswerInfo> getDisapprovedAnswers(String studentId, RowBounds rowBounds);
}
