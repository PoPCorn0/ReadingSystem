/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.12
 *
 * @Description
 */

package com.snsoft.readingsystem.dao;

import com.snsoft.readingsystem.pojo.PendingTask;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingTaskDao {

    // 添加一个待审核任务
    @Insert("insert into pending_task (id, author_id, title, content, team_id)values(#{id}, #{authorId}, #{title}, " +
            "#{content}, #{teamId})")
    public int commitTask(PendingTask pendingTask);

    // 根据id查询待审核任务
    @Select("select id, author_id, title, content, commit_time, check_time, check_mark, reason, team_id from " +
            "pending_task where id = #{id}")
    public PendingTask getPendingTaskById(String id);

    // 根据id删除待审核任务
    @Delete("delete from pendingTask where id = #{id}")
    public int deletePendingTaskById(String id);
}
