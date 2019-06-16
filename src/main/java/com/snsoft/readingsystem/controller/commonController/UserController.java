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

package com.snsoft.readingsystem.controller.commonController;

import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.service.UserService;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import com.snsoft.readingsystem.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller("CommonUserController")
@RequestMapping("/common")
public class UserController {
    @Resource
    UserService userService;

    /**
     * 获取个人信息，等待个人的信息完善后将返回更多数据
     *
     * @param studentId 学生id
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/getPersonalInfo", method = RequestMethod.GET)
    public ModelAndView getPersonalInfo(@RequestParam("id") String studentId) {
        try {
            return userService.getPersonalInfo(studentId);
        } catch (RuntimeException e) {
            return ModelAndViewUtil.getModelAndView(Code.ERROR);
        }
    }

    /**
     * 更新密码，用户需要输入旧密码
     *
     * @param user   session中用户信息
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public ModelAndView resetPwd(@SessionAttribute("user") User user,
                                 @RequestParam("oldPwd") String oldPwd,
                                 @RequestParam("newPwd") String newPwd) {
        try {
            return userService.resetPwd(user, oldPwd, newPwd);
        } catch (RuntimeException e) {
            return ModelAndViewUtil.getModelAndView(Code.ERROR);
        }
    }
}
