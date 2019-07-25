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

import com.snsoft.readingsystem.dao.PendingTaskDao;
import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.pojo.PendingTask;
import com.snsoft.readingsystem.pojo.User;
import com.snsoft.readingsystem.service.PendingTaskService;
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

@Controller("StudentPendingTaskController")
@RequestMapping("/student")
public class PendingTaskController {

    @Resource
    PendingTaskService pendingTaskService;
    @Resource
    PendingTaskDao pendingTaskDao;
    @Resource
    ModelAndView mv;

    @RequestMapping(value = "/commitTask", method = RequestMethod.POST)
    public ModelAndView commitTask(@SessionAttribute("user") User user,
                                   @RequestParam("title") String title,
                                   @RequestParam("content") String content,
                                   @RequestParam("teamId") String teamId) {
        PendingTask pendingTask = new PendingTask();
        pendingTask.setId(UUID.randomUUID().toString());
        pendingTask.setAuthorId(user.getId());
        pendingTask.setTeamId(teamId);
        pendingTask.setTitle(title);
        pendingTask.setContent(content);

        try {
            return pendingTaskService.commitTask(pendingTask);
        } catch (RuntimeException e) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }

    @RequestMapping(value = "/deletePendingTask", method = RequestMethod.POST)
    public ModelAndView deletePendingTask(@SessionAttribute("user") User user,
                                          @RequestParam("id") String id) {
        try {
            return pendingTaskService.deletePendingTask(user.getId(), id);
        } catch (RuntimeException e) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }
    /**
     * 获取已通过审核的任务
     *
     * @param user session中用户信息
     * @param page 分页参数
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/getApprovedTask", method = RequestMethod.GET)
    public ModelAndView getApprovedTask(@SessionAttribute("user") User user,
                                        @RequestParam(value = "page", required = false) Integer page) {
        RowBounds rowBounds = PageUtil.getRowBounds(page);
        try {
            List<PendingTask> approvedTasks = pendingTaskDao.getApprovedTasksByStudentId(user.getId(), rowBounds);
            if (approvedTasks == null) {
                return ModelAndViewUtil.addObject(mv, Code.FAIL);
            }
            return ModelAndViewUtil.addObject(mv, "data", approvedTasks);
        } catch (RuntimeException e) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }

    /**
     * 获取未通过审核的任务
     *
     * @param user session中用户信息
     * @param page 分页参数
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/getDisapprovedTask", method = RequestMethod.GET)
    public ModelAndView getDisapprovedTask(@SessionAttribute("user") User user,
                                           @RequestParam(value = "page", required = false) Integer page) {
        RowBounds rowBounds = PageUtil.getRowBounds(page);
        try {
            List<PendingTask> DisapprovedTasks = pendingTaskDao.getDisapprovedTasksByStudentId(user.getId(), rowBounds);
            if (DisapprovedTasks == null) {
                return ModelAndViewUtil.addObject(mv, Code.FAIL);
            }
            return ModelAndViewUtil.addObject(mv, "data", DisapprovedTasks);
        } catch (RuntimeException e) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }
}