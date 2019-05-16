/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.15
 *
 * @Description
 */

package com.snsoft.readingsystem.service;

import com.snsoft.readingsystem.dao.UserDao;
import com.snsoft.readingsystem.pojo.Student;
import com.snsoft.readingsystem.returnPojo.PersonalInfo;
import com.snsoft.readingsystem.utils.AllConstant;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserDao userDao;

    /**
     * 根据学生id获取个人信息
     *
     * @param studentId 学生id
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView getPersonalInfo(String studentId) {
        Student student = userDao.getStudentByIdNotRemoved(studentId);
        if (student == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "该用户不存在");
        }

        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setId(studentId);
        personalInfo.setName(student.getName());
        personalInfo.setScore(student.getScore());

        personalInfo.setPraiseAmount(userDao.getPraiseAmount(studentId));
        personalInfo.setTaskAmount(userDao.getTaskAmount(studentId));
        personalInfo.setAnswerAmount(userDao.getAnswerAmount(studentId));

        return ModelAndViewUtil.getModelAndView("data", personalInfo);
    }
}
