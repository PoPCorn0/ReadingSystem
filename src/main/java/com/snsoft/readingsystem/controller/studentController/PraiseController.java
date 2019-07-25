/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.07.25
 *
 * @Description
 */

package com.snsoft.readingsystem.controller.studentController;

import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.pojo.User;
import com.snsoft.readingsystem.service.PraiseService;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller("StudentPraiseController")
@RequestMapping("student")
public class PraiseController {

    @Resource
    PraiseService praiseService;
    @Resource
    ModelAndView mv;

    /**
     * 点赞
     *
     * @param user session中用户信息
     * @param id   解读&追加解读id
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/praise", method = RequestMethod.POST)
    public ModelAndView praise(@SessionAttribute("user") User user,
                               @RequestParam("id") String id) {
        try {
            return praiseService.praise(user.getId(), id);
        } catch (RuntimeException e) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }
}
