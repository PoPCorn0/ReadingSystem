/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.07
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
    public Teacher getTeacherById(@Param("id") String id);

    // 根据id查找学生
    @Select("select id, name, pwd, score from student where id = #{id}")
    public Student getStudentById(@Param("id") String id);

    // 根据id查找未移除的学生
    @Select("select id, name, pwd, score from student where id = #{id} and is_remove = '0'")
    public Student getStudentByIdNotRemoved(@Param("id") String id);

    // 添加学生
    @Insert("insert into student (id, name, pwd, score) values (#{id}, #{name}, #{pwd}, #{score})")
    public int addStudent(Student student);

    // 更新学生信息
    @Update("update student set name = #{name}, pwd = #{pwd}, score = #{score}, is_remove = #{isRemove} where id = " +
            "#{id}")
    public int updateStudent(Student student);

    // 移除学生
    @Update("update student set is_remove = '1' where id = #{id}")
    public int removeStudent(@Param("id") String id);
}
