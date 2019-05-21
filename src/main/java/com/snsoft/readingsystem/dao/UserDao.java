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

import com.snsoft.readingsystem.pojo.Student;
import com.snsoft.readingsystem.pojo.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    // 根据id查找导师
    @Select("select id, pwd from teacher where id=#{id}")
    Teacher getTeacherById(String id);

    // 根据id查找学生
    @Select("select id, name, pwd, score from student where id = #{id}")
    Student getStudentById(String id);

    // 根据id查找未移除的学生
    @Select("select id, name, pwd, score from student where id = #{id} and is_remove = '0'")
    Student getStudentByIdNotRemoved(String id);

    // 添加学生
    @Insert("insert into student (id, name, pwd, score) values (#{id}, #{name}, #{pwd}, #{score})")
    int addStudent(Student student);

    // 更新学生信息
    @Update("update student set name = #{name}, pwd = #{pwd}, score = #{score}, is_remove = #{isRemove} " +
            "where id = #{id}")
    int updateStudent(Student student);

    // 移除学生
    @Update("update student set is_remove = '1' where id = #{id}")
    int removeStudent(String id);

    // 根据不同条目更新学生积分
    int updateScore(@Param("studentId") String studentId,
                    @Param("teamId") String teamId,
                    @Param("item") String item);

    // 根据学生id增加指定积分
    @Update("update student set score = score + #{score} where id = #{studentId}")
    int addScore(@Param("studentId") String studentId, @Param("score") int score);

    // 根据id获取所有解读总赞数
    @Select("select sum(praise_amount) from answer where author_id = #{studentId}")
    int getPraiseAmount(String studentId);

    // 根据id获取所有提交通过任务数
    @Select("select count(id) from task where author_id = #{studentId}")
    int getTaskAmount(String studentId);

    // 根据id获取所有提交通过解读数
    @Select("select count(id) from answer where author_id = #{studentId}")
    int getAnswerAmount(String studentId);

    // 根据导师id更新密码
    @Update("update teacher set pwd = #{newPwd} where id = #{teacherId}")
    int resetTeacherPwd(@Param("teacherId") String teacherId, @Param("newPwd") String newPwd);

    // 根据学生id更新密码
    @Update("update student set pwd = #{newPwd} where id = #{studentId} and is_remove = '0'")
    int resetStudentPwd(@Param("studentId") String studentId, @Param("newPwd") String newPwd);
}
