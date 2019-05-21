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

import com.snsoft.readingsystem.pojo.PendingTask;
import com.snsoft.readingsystem.returnPojo.PendingTaskInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PendingTaskDao {

    // 添加一个待审核任务
    @Insert("insert into pending_task (id, author_id, title, content, team_id)values(#{id}, #{authorId}, #{title}, " +
            "#{content}, #{teamId})")
    int commitTask(PendingTask pendingTask);

    // 根据id查询待审核任务
    @Select("select id, author_id, title, content, commit_time, check_time, check_mark, reason, team_id from " +
            "pending_task where id = #{id}")
    PendingTask getPendingTaskById(String id);

    // 根据id删除待审核任务
    @Delete("delete from pendingTask where id = #{id}")
    int deletePendingTaskById(String id);

    // 根据学生id查询通过审核的任务
    List<PendingTask> getApprovedTasksByStudentId(String studentId, RowBounds rowBounds);

    // 根据学生id查询未通过审核的任务
    List<PendingTask> getDisapprovedTasksByStudentId(String studentId, RowBounds rowBounds);

    // 根据学生id查询尚未审核的任务
    List<PendingTaskInfo> getStudentPendingTasks(String studentId, RowBounds rowBounds);

    // 根据导师id查询所创建的所有团队的尚未审核的任务
    List<PendingTaskInfo> getTeacherPendingTasks(String teacherId, RowBounds rowBounds);

    // 更新待审核任务表中的记录
    @Update("update pending_task set check_mark = #{checkMark}, check_time = now(), reason = #{reason} " +
            "where id = #{id}")
    int updatePendingTask(PendingTask pendingTask);

    // 删除团队时将所有该团队的待审核任务设为不通过,参数studentId为null，
    // 删除团队成员时将该团队属于这个成员的待审核任务设为不通过，参数studentId不为null
    void setPendingTaskDisapproved(@Param("teamId") String teamId, @Param("studentId") String studentId);
}
