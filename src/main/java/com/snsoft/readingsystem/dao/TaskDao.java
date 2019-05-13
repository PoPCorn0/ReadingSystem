/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.10
 *
 * @Description
 */

package com.snsoft.readingsystem.dao;

import com.snsoft.readingsystem.pojo.StuTask;
import com.snsoft.readingsystem.pojo.Task;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDao {

    // 发布任务
    @Insert("insert into task (id, team_id, author_id, reward, title, content, end_time) values(" +
            "#{id}, #{teamId}, #{authorId}, #{reward}, #{title}, #{content}, #{endTime})")
    public int publishTask(Task task);

    // 为发布的任务添加可接受者
    public int addReceiver(List<StuTask> receiver);

    // 根据id查询可接受的任务
    @Select("select id, team_id, author_id, reward, title, content, commit_time, end_time from task " +
            "where id = #{id} and end_time > now() and is_final = '0'")
    public Task getAcceptableTaskById(String id);

    // 根据任务id从stu_task表中删除可接受者
    @Delete("delete from stu_task where task_id = #{taskId}")
    public void deleteReceiverByTaskIdAndTeamId(@Param("taskId") String taskId);

    // 根据id删除已发布任务
    @Delete("delete from task where id = #{id}")
    public int deleteTask(String id);

    // 删除团队前更新未截止的任务的可接受标记
    @Update("update task set is_final = '1' where end_time > now() and team_id = #{teamId}")
    public void setTaskFinalByTeamId(@Param("teamId") String teamId);

    // 删除团队中学生前更新该团队中属于该学生发布的任务的可提交解读标记
    @Update("update task set is_final = #{isFinal} " +
            "where end_time > now() and team_id = #{teamId} and author_id = #{studentId}")
    public void updateTaskByTeamIdAndStudentId(@Param("teamId") String teamId,
                                               @Param("studentId") String studentId,
                                               @Param("isFinal") char isFinal);

    // 根据任务id和学生id查询是否可以接受该任务
    @Select("select task_id, student_id from stu_task where task_id = #{taskId} and student_id = #{studentId}")
    public StuTask getStuTaskByTaskIdAndStudentId(@Param("taskId") String taskId, @Param("studentId") String studentId);
}
