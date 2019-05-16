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

import com.snsoft.readingsystem.dao.MessageDao;
import com.snsoft.readingsystem.utils.AllConstant;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import com.snsoft.readingsystem.utils.PageUtil;
import com.snsoft.readingsystem.utils.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller("Common_MessageController")
@RequestMapping("/common")
public class MessageController {
    @Resource
    MessageDao messageDao;

    /**
     * 获取所有消息通知
     *
     * @param user session中用户信息
     * @param page 分页参数
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public ModelAndView getMessage(@SessionAttribute("user") User user,
                                   @RequestParam(value = "page", required = false) Integer page) {
        RowBounds rowBounds = PageUtil.getRowBounds(page);
        try {
            return ModelAndViewUtil.getModelAndView("data", messageDao.getMessages(user.getId(), rowBounds));
        } catch (RuntimeException e) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_ERROR);
        }
    }

    /**
     * 删除一条消息通知
     *
     * @param id 消息id
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/deleteMessage", method = RequestMethod.POST)
    public ModelAndView deleteMessage(@RequestParam("id") String id) {
        try {
            return messageDao.deleteMessage(id) == 1 ?
                    ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS) :
                    ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "消息不存在");
        } catch (RuntimeException e) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_ERROR);
        }
    }
}
