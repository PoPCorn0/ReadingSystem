/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.13
 *
 * @Description
 */

package com.snsoft.readingsystem.controller.studentController;

import com.snsoft.readingsystem.dao.PendingAnswerDao;
import com.snsoft.readingsystem.pojo.PendingAnswer;
import com.snsoft.readingsystem.returnPojo.PendingAnswerInfo;
import com.snsoft.readingsystem.service.PendingAnswerService;
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
import java.util.List;
import java.util.UUID;

@Controller
public class PendingAnswerController {
    @Resource
    PendingAnswerService pendingAnswerService;
    @Resource
    PendingAnswerDao pendingAnswerDao;

    /**
     * 提交待审核解读
     *
     * @param user           sess中用户信息
     * @param receivedTaskId 已接受任务id
     * @param title          标题
     * @param content        内容
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/student/commitAnswer", method = RequestMethod.POST)
    public ModelAndView commitAnswer(@SessionAttribute("user") User user,
                                     @RequestParam("receivedTaskId") String receivedTaskId,
                                     @RequestParam("title") String title,
                                     @RequestParam("content") String content) {
        PendingAnswer pendingAnswer = new PendingAnswer();
        pendingAnswer.setId(UUID.randomUUID().toString());
        pendingAnswer.setAuthorId(user.getId());
        pendingAnswer.setTitle(title);
        pendingAnswer.setContent(content);
        pendingAnswer.setReceivedTaskId(receivedTaskId);

        try {
            return pendingAnswerService.commitAnswer(pendingAnswer);
        } catch (RuntimeException e) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_ERROR);
        }
    }

    /**
     * 删除待审核解读
     *
     * @param user session中用户信息
     * @param id   待审核解读id
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/student/deletePendingAnswer", method = RequestMethod.POST)
    public ModelAndView deletePendingAnswer(@SessionAttribute("user") User user,
                                            @RequestParam("id") String id) {
        try {
            return pendingAnswerService.deletePendingAnswer(user.getId(), id);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_ERROR);
        }
    }

    /**
     * 获取已通过审核解读列表
     *
     * @param user session中用户信息
     * @param page 分页参数
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/student/getApprovedAnswer", method = RequestMethod.GET)
    public ModelAndView getApprovedAnswer(@SessionAttribute("user") User user,
                                          @RequestParam(value = "page", required = false) Integer page) {
        RowBounds rowBounds = PageUtil.getRowBounds(page);

        List<PendingAnswerInfo> approvedAnswers = pendingAnswerDao.getApprovedAnswers(user.getId(), rowBounds);

        if (approvedAnswers == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
        }

        return ModelAndViewUtil.getModelAndView("data", approvedAnswers);
    }

    /**
     * 获取未通过审核解读列表
     *
     * @param user session中用户信息
     * @param page 分页参数
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/student/getDisapprovedAnswer", method = RequestMethod.GET)
    public ModelAndView getDisapprovedAnswer(@SessionAttribute("user") User user,
                                             @RequestParam(value = "page", required = false) Integer page) {
        RowBounds rowBounds = PageUtil.getRowBounds(page);

        List<PendingAnswerInfo> disapprovedAnswers = pendingAnswerDao.getDisapprovedAnswers(user.getId(), rowBounds);

        if (disapprovedAnswers == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
        }

        return ModelAndViewUtil.getModelAndView("data", disapprovedAnswers);
    }
}
