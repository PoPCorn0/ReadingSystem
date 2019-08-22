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

import com.snsoft.teamreading.pojo.StuTask;
import com.snsoft.teamreading.pojo.Task;
import com.snsoft.teamreading.returnPojo.AcceptedTaskInfo;
import com.snsoft.teamreading.returnPojo.TaskDetailInfo;
import com.snsoft.teamreading.returnPojo.TaskInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDao {

    // 发布任务
    @Insert("insert into task (id, team_id, author_id, reward, title, content, end_time) values(" +
            "#{id}, #{teamId}, #{authorId}, #{reward}, #{title}, #{content}, #{endTime})")
    int publishTask(Task task);

    // 为发布的任务添加可接受者
    int addReceiver(List<StuTask> receiver);

    // 根据id查询可接受的任务
    @Select("select id, team_id, author_id, reward, title, content, commit_time, end_time from task " +
            "where id = #{id} and end_time > now() and is_final = '0'")
    Task getAcceptableTaskById(String id);

    // 根据任务id从stu_task表中删除可接受者
    @Delete("delete from stu_task where task_id = #{taskId}")
    void deleteReceiverByTaskIdAndTeamId(String taskId);

    // 根据id删除已发布任务
    @Delete("delete from task where id = #{id}")
    int deleteTask(String id);

    // 删除团队前更新未截止的任务的可接受标记
    @Update("update task set is_final = '1' where end_time > now() and team_id = #{teamId}")
    void setTaskFinalByTeamId(String teamId);

    // 删除团队中学生前更新该团队中属于该学生发布的任务的可提交解读标记
    @Update("update task set is_final = #{isFinal} " +
            "where end_time > now() and team_id = #{teamId} and author_id = #{studentId}")
    void updateTaskByTeamIdAndStudentId(@Param("teamId") String teamId,
                                        @Param("studentId") String studentId,
                                        @Param("isFinal") char isFinal);

    // 根据任务id和学生id查询是否可以接受该任务
    @Select("select task_id, student_id from stu_task where task_id = #{taskId} and student_id = #{studentId}")
    StuTask getStuTaskByTaskIdAndStudentId(@Param("taskId") String taskId,
                                           @Param("studentId") String studentId);

    // 根据学生id查询已接受的任务
    List<AcceptedTaskInfo> getAcceptedTasksInfoByStudentId(String userId, RowBounds rowBounds);

    // 根据id查询任务
    @Select("select id, team_id, author_id, reward, title, content, commit_time, end_time from task where id = #{id}")
    Task getTaskById(String taskId);

    // 根据导师id获取已发布任务
    List<TaskInfo> getTeacherTaskInfo(String teacherId, RowBounds rowBounds);

    // 根据学生id获取已发布任务
    List<TaskInfo> getStudentTaskInfo(String studentId, RowBounds rowBounds);

    // 根据任务id查看任务详情
    TaskDetailInfo getTaskDetail(String taskId);

    // 根据已接受任务id获取任务
    @Select("select id, team_id, author_id, author_id, reward, title, content, commit_time, end_time from task " +
            "where id = (select received_task.task_id from received_task where received_task.id = #{receivedTaskId} )")
    Task getTaskByReceivedTaskId(String receivedTaskId);

    // 删除团队时将该团队尚未结束的任务的可接受者从stu_task表中删除
    @Delete("delete from stu_task " +
            "where task_id in ( " +
            "select task.id from task " +
            "where task.team_id = #{teamId} " +
            "and task.end_time > now() )")
    void deleteStuTaskByTeamId(String teamId);
}
