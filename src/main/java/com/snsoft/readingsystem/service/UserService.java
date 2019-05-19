/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.19
 *
 * @Description
 */

package com.snsoft.readingsystem.service;

import com.snsoft.readingsystem.dao.UserDao;
import com.snsoft.readingsystem.pojo.Student;
import com.snsoft.readingsystem.returnPojo.PersonalInfo;
import com.snsoft.readingsystem.utils.AllConstant;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import com.snsoft.readingsystem.utils.User;
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

    @Transactional
    public ModelAndView resetPwd(User user, String oldPwd, String newPwd) {

        // 判断用户输入的旧密码是否匹配
        if (!user.getPwd().equals(oldPwd)) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "旧密码验证失败");
        }

        // 更新密码
        if (user.getIdentityMark() == AllConstant.IDENTITYMARK_TEACHER) {
            return userDao.resetTeacherPwd(user.getId(), newPwd) == 1 ?
                    ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS) :
                    ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
        } else {
            return userDao.resetStudentPwd(user.getId(), newPwd) == 1 ?
                    ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS) :
                    ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
        }
    }
}
