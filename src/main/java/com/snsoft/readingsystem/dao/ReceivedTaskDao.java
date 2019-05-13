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

import com.snsoft.readingsystem.pojo.ReceivedTask;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceivedTaskDao {

    // 根据任务id查询已接受任务记录
    @Select("select id, task_id, receiver_id, receive_time from received_task " +
            "where task_id = #{task_id} and is_final = '0'")
    public List<ReceivedTask> getReceivedTasksByTaskIdNotFinal(String taskId);

    // 删除团队前将该团队的已接受任务设为不可提交解读
    @Update("update received_task set is_final = '1' where team_id = #{teamId}")
    public void setReceivedTaskFinalByTeamId(String teamId);

    // 删除团队中学生前将该团队属于该学生的已接受任务设为不可提交解读
    @Update("update received_task set received_task.is_final = #{isFinal}" +
            "where received_task.receiver_id = #{studentId} " +
            "and received_task.task_id in " +
            "(" +
            "select task.team_id from task where task.team_id = #{teamId}" +
            ") ")
    public void updateReceivedTaskByTeamIdAndStudentId(@Param("teamId") String teamId,
                                                       @Param("studentId") String studentId,
                                                       @Param("isFinal") char isFinal);


    // 根据任务id和用户id查询是否已接受该任务
    @Select("select id,  task_id, receiver_id, receive_time from received_task where " +
            "task_id = #{taskId} and receiver_id = #{studentId} and is_final = '0'")
    public ReceivedTask getReceivedTaskByTaskIdAndStudentId(@Param("taskId") String taskId,
                                                            @Param("studentId") String studentId);

    // 添加已接受任务
    @Insert("insert into received_task (id, receiver_id, task_id)  values (#{id}, #{receiverId}, #{taskId})")
    public int addReceivedTask(ReceivedTask receivedTask);
}
