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

import com.snsoft.teamreading.pojo.PayRecord;
import com.snsoft.teamreading.pojo.PraiseRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordDao {
    // 根据学生id查询当天的点赞记录
    @Select("select id, answer_id, praise_id, praise_time from praise_record " +
            "where to_days(praise_time) = to_days(now()) and praise_id = #{studentId}")
    List<PraiseRecord> getPraiseRecordsByStudentIdInADay(String studentId);

    // 根据学生id、解读id查询点赞记录
    @Select("select id, answer_id, praise_id, praise_time from praise_record " +
            "where answer_id = #{answerId} and praise_id = #{studentId}")
    PraiseRecord getPraiseRecordByStudentIdAndAnswerId(@Param("studentId") String studentId,
                                                       @Param("answerId") String answerId);

    // 添加点赞记录
    @Insert("insert into praise_record (id, answer_id, praise_id) values (#{id}, #{answerId}, #{praiseId})")
    int addPraiseRecord(PraiseRecord praiseRecord);

    // 根据学生id和解读d查询付费记录
    @Select("select id, pay_id, answer_id, received_task_id, pay_time from pay_record " +
            "where pay_id = #{studentId} and answer_id = #{answerId}")
    PayRecord getPayRecordByStudentIdAndAnswerId(@Param("studentId") String studentId,
                                                 @Param("answerId") String answerId);

    // 添加一条付费查看解读记录
    @Insert("insert into pay_record (id, pay_id, answer_id, received_task_id) " +
            "values(#{id}, #{payId}, #{answerId}, #{receivedTaskId})")
    void addPayRecord(PayRecord payRecord);
}
