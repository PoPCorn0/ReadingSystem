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

package com.snsoft.teamreading.service;

import com.snsoft.teamreading.dao.UserDao;
import com.snsoft.teamreading.enums.Code;
import com.snsoft.teamreading.enums.Identity;
import com.snsoft.teamreading.pojo.Student;
import com.snsoft.teamreading.pojo.User;
import com.snsoft.teamreading.returnPojo.PersonalInfo;
import com.snsoft.teamreading.utils.ModelAndViewUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserDao userDao;
    @Resource
    ModelAndView mv;

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
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "该用户不存在");
        }

        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setId(studentId);
        personalInfo.setName(student.getName());
        personalInfo.setScore(student.getScore());

        personalInfo.setPraiseAmount(userDao.getPraiseAmount(studentId));
        personalInfo.setTaskAmount(userDao.getTaskAmount(studentId));
        personalInfo.setAnswerAmount(userDao.getAnswerAmount(studentId));

        return ModelAndViewUtil.addObject(mv, "data", personalInfo);
    }

    @Transactional
    public ModelAndView resetPwd(User user, String oldPwd, String newPwd) {

        // 判断用户输入的旧密码是否匹配
        if (!user.getPwd().equals(oldPwd)) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "原密码验证失败");
        }

        // 更新密码
        if (user.getIdentityMark() == Identity.TEACHER.getIdentity()) {
            return userDao.resetTeacherPwd(user.getId(), newPwd) == 1 ?
                    ModelAndViewUtil.addObject(mv, Code.SUCCESS) :
                    ModelAndViewUtil.addObject(mv, Code.FAIL);
        } else {
            return userDao.resetStudentPwd(user.getId(), newPwd) == 1 ?
                    ModelAndViewUtil.addObject(mv, Code.SUCCESS) :
                    ModelAndViewUtil.addObject(mv, Code.FAIL);
        }
    }
}
