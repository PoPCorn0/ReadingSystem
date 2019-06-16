/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.06.16
 *
 * @Description
 */

package com.snsoft.readingsystem.service;

import com.snsoft.readingsystem.dao.TeamDao;
import com.snsoft.readingsystem.dao.UserDao;
import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.pojo.Student;
import com.snsoft.readingsystem.pojo.Team;
import com.snsoft.readingsystem.pojo.TeamStu;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentService {
    @Resource
    UserDao userDao;
    @Resource
    TeamDao teamDao;

    /**
     * 添加一个学生
     *
     * @param student 学生对象
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView addStudent(Student student) {
        Student studentById = userDao.getStudentById(student.getId());
        //判断该id是否已经存在
        if (studentById != null && studentById.getIsRemoved() != '1') {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "该ID已存在");
        } else if (studentById == null) {
            //添加学生
            return userDao.addStudent(student) == 1 ?
                    ModelAndViewUtil.getModelAndView(Code.SUCCESS) :
                    ModelAndViewUtil.getModelAndView(Code.FAIL);
        } else {
            //如果输入的是已移除的学生id，则将移除标记设为否
            student.setIsRemoved('0');
            return userDao.updateStudent(student) == 1 ?
                    ModelAndViewUtil.getModelAndView(Code.SUCCESS) :
                    ModelAndViewUtil.getModelAndView(Code.FAIL);
        }
    }

    /**
     * 删除学生，可以被恢复
     *
     * @param teacherId 导师id
     * @param studentId 学生id
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView deleteStudent(String teacherId, String studentId) {
        Student student = userDao.getStudentByIdNotRemoved(studentId);
        if (student == null) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "该学生不存在");
        }

        List<TeamStu> teamStuList = teamDao.getTeamStuByStudentId(studentId);
        for (TeamStu e : teamStuList) {
            Team team = teamDao.getTeamById(e.getTeamId());
            if (!team.getTeacherId().equals(teacherId)) {
                return ModelAndViewUtil.getModelAndView(Code.FAIL, "该学生还属于其他团队，无法移除");
            }
        }

        return userDao.removeStudent(studentId) == 1 ?
                ModelAndViewUtil.getModelAndView(Code.SUCCESS) :
                ModelAndViewUtil.getModelAndView(Code.FAIL);
    }
}
