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

import com.snsoft.readingsystem.dao.FeedbackDao;
import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.pojo.Feedback;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import com.snsoft.readingsystem.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.UUID;

@Controller("CommonFeedbackController")
@RequestMapping("/common")
public class FeedbackController {

    @Resource
    FeedbackDao feedbackDao;

    /**
     * 添加反馈
     *
     * @param user    sess中用户信息
     * @param content 反馈内容
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public ModelAndView feedback(@SessionAttribute(value = "user") User user,
                                 @RequestParam("content") String content) {
        Feedback feedback = new Feedback();
        feedback.setId(UUID.randomUUID().toString());
        feedback.setAuthorId(user.getId());
        feedback.setContent(content);

        try {
            return feedbackDao.addFeedback(feedback) == 1 ?
                    ModelAndViewUtil.getModelAndView(Code.SUCCESS) :
                    ModelAndViewUtil.getModelAndView(Code.FAIL);
        } catch (RuntimeException e) {
            return ModelAndViewUtil.getModelAndView(Code.ERROR);
        }
    }
}
