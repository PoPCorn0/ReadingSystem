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

package com.snsoft.readingsystem.controller.commonController;

import com.snsoft.readingsystem.dao.AnswerDao;
import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.enums.Identity;
import com.snsoft.readingsystem.pojo.User;
import com.snsoft.readingsystem.service.AnswerService;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import com.snsoft.readingsystem.utils.PageUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller("CommonAnswerController")
@RequestMapping("/common")
public class AnswerController {

    @Resource
    AnswerDao answerDao;
    @Resource
    AnswerService answerService;
    @Resource
    ModelAndView mv;

    /**
     * 如果当前是导师账号则返回其创建的所有团队的解读，如果是学生账号则返回其所在的所有团队的解读
     *
     * @param user session中用户信息
     * @param page 分页参数
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/getAnswer", method = RequestMethod.GET)
    public ModelAndView getAnswer(@SessionAttribute("user") User user,
                                  @RequestParam(value = "page", required = false) Integer page) {
        RowBounds rowBounds = PageUtil.getRowBounds(page);

        try {
            if (user.getIdentityMark() == Identity.TEACHER.getIdentity())
                return ModelAndViewUtil.addObject(mv, "data",
                        answerDao.getTeacherAnswerInfo(user.getId(), rowBounds));
            else {
                return ModelAndViewUtil.addObject(mv, "data",
                        answerDao.getStudentAnswerInfo(user.getId(), rowBounds));
            }
        } catch (RuntimeException e) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }

    /**
     * 查看解读详情，如果当前用户是学生且未曾付费时需要付费
     *
     * @param user session中用户信息
     * @param id   解读id
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/getAnswerDetail", method = RequestMethod.GET)
    public ModelAndView getAnswerDetail(@SessionAttribute("user") User user,
                                        @RequestParam("id") String id) {
        try {
            return answerService.getAnswerDetail(user, id);
        } catch (RuntimeException e) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }
}
