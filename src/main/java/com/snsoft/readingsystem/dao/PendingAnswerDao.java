/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.21
 *
 * @Description
 */

package com.snsoft.readingsystem.dao;

import com.snsoft.readingsystem.pojo.PendingAnswer;
import com.snsoft.readingsystem.returnPojo.PendingAnswerInfo;
import org.apache.ibatis.annotations.*;
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
    PendingAnswer getPendingAnswerByReceivedTaskId(@Param("receivedTaskId") String receivedTaskId);

    // 添加待审核解读
    @Insert("insert into pending_answer " +
            "(id, received_task_id, author_id, title, content) " +
            "values (#{id}, #{receivedTaskId}, #{authorId}, #{title}, #{content})")
    int addPendingAnswer(PendingAnswer pendingAnswer);

    // 根据id查询待审核解读
    @Select("select " +
            "id, received_task_id, author_id, title, content, commit_time, check_time, check_mark, reason " +
            "from pending_answer " +
            "where id = #{id}")
    PendingAnswer getPendingAnswerById(@Param("id") String id);

    // 根据id删除待审核解读
    @Delete("delete from pending_answer where id = #{id}")
    int deletePendingAnswer(@Param("id") String id);

    // 根据学生id查询已通过审核的解读
    List<PendingAnswerInfo> getApprovedAnswers(String studentId, RowBounds rowBounds);

    // 根据学生id查询未通过审核的解读
    List<PendingAnswer> getDisapprovedAnswers(String studentId, RowBounds rowBounds);

    // 根据学生id查询所有尚未审核的解读
    List<PendingAnswerInfo> getStudentPendingAnswerInfo(String id, RowBounds rowBounds);

    // 根据导师id查询创建的团队的所有尚未审核的解读
    List<PendingAnswerInfo> getTeacherPendingAnswerInfo(String id, RowBounds rowBounds);

    // 根据id更新待审核解读&追加解读记录
    @Update("update pending_answer set check_mark = #{checkMark} , reason = #{reason} where id = #{id}")
    int checkAnswer(PendingAnswer pendingAnswer);

    // 删除团队时将该团队所有待审核解读设为不通过,参数studentId为null，
    // 删除团队成员时将该团队属于这个成员的待审核解读设为不通过，参数studentId不为null
    void setPendingAnswerDisapproved(@Param("teamId") String teamId, @Param("studentId") String studentId);
}
