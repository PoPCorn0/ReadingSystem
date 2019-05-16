/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.14
 *
 * @Description
 */

package com.snsoft.readingsystem.dao;

import com.snsoft.readingsystem.pojo.Answer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerDao {

    // 根据id查询解读
    @Select("select id, task_id, received_task_id, author_id, tier, title, content, commit_time, praise_amount " +
            "from answer " +
            "where id = #{id}")
    public Answer getAnswerById(@Param("id") String answerId);
}
