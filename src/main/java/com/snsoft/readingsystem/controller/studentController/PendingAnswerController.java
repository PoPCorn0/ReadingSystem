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

import com.snsoft.readingsystem.dao.PendingAnswerDao;
import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.pojo.PendingAnswer;
import com.snsoft.readingsystem.pojo.User;
import com.snsoft.readingsystem.returnPojo.PendingAnswerInfo;
import com.snsoft.readingsystem.service.PendingAnswerService;
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
import java.util.List;
import java.util.UUID;

@Controller("StudentAnswerController")
@RequestMapping("/student")
public class PendingAnswerController {
    @Resource
    PendingAnswerService pendingAnswerService;
    @Resource
    PendingAnswerDao pendingAnswerDao;
    @Resource
    ModelAndView mv;

    /**
     * 提交待审核解读
     *
     * @param user           sess中用户信息
     * @param receivedTaskId 已接受任务id
     * @param title          标题
     * @param content        内容
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/commitAnswer", method = RequestMethod.POST)
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
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }

    /**
     * 删除待审核解读
     *
     * @param user session中用户信息
     * @param id   待审核解读id
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/deletePendingAnswer", method = RequestMethod.POST)
    public ModelAndView deletePendingAnswer(@SessionAttribute("user") User user,
                                            @RequestParam("id") String id) {
        try {
            return pendingAnswerService.deletePendingAnswer(user.getId(), id);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }

    /**
     * 获取已通过审核解读列表
     *
     * @param user session中用户信息
     * @param page 分页参数
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/getApprovedAnswer", method = RequestMethod.GET)
    public ModelAndView getApprovedAnswer(@SessionAttribute("user") User user,
                                          @RequestParam(value = "page", required = false) Integer page) {
        RowBounds rowBounds = PageUtil.getRowBounds(page);

        List<PendingAnswerInfo> approvedAnswers = pendingAnswerDao.getApprovedAnswers(user.getId(), rowBounds);

        if (approvedAnswers == null) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL);
        }

        return ModelAndViewUtil.addObject(mv, "data", approvedAnswers);
    }

    /**
     * 获取未通过审核解读列表
     *
     * @param user session中用户信息
     * @param page 分页参数
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/getDisapprovedAnswer", method = RequestMethod.GET)
    public ModelAndView getDisapprovedAnswer(@SessionAttribute("user") User user,
                                             @RequestParam(value = "page", required = false) Integer page) {
        RowBounds rowBounds = PageUtil.getRowBounds(page);

        List<PendingAnswer> disapprovedAnswers = pendingAnswerDao.getDisapprovedAnswers(user.getId(), rowBounds);

        if (disapprovedAnswers == null) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL);
        }

        return ModelAndViewUtil.addObject(mv, "data", disapprovedAnswers);
    }
}
