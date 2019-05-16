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

import com.snsoft.readingsystem.pojo.PraiseRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PraiseDao {
    // 根据学生id查询当天的点赞记录
    @Select("select id, answer_id, praise_id, praise_time from praise_record " +
            "where to_days(praise_time) = to_days(now())")
    public List<PraiseRecord> getPraiseRecordsByStudentIdInADay(@Param("id") String studentId);

    // 根据学生id、解读id查询点赞记录
    @Select("select id, answer_id, praise_id, praise_time from praise_record " +
            "where answer_id = #{answerId} and praise_id = #{studentId}")
    public PraiseRecord getPraiseRecordByStudentIdAndAnswerId(@Param("studentId") String studentId,
                                                              @Param("answerId") String answerId);

    // 添加点赞记录
    @Insert("insert into praise_record (id, answer_id, praise_id) values (#{id}, #{answerId}, #{praiseId})")
    public int addPraiseRecord(PraiseRecord praiseRecord);
}
