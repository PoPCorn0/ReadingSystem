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

package com.snsoft.readingsystem.controller.commonController;

import com.snsoft.readingsystem.service.UserService;
import com.snsoft.readingsystem.utils.AllConstant;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller("Common_UserController")
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
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_ERROR);
        }
    }
}
