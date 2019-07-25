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

package com.snsoft.readingsystem.controller.teacherController;

import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.pojo.Task;
import com.snsoft.readingsystem.pojo.User;
import com.snsoft.readingsystem.service.TaskService;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Controller(value = "TeacherTaskController")
@RequestMapping("teacher")
public class TaskController {
    @Resource
    TaskService taskService;
    @Resource
    ModelAndView mv;

    /**
     * 发布任务
     *
     * @param user     session中当前用户信息
     * @param teamId   团队id
     * @param reward   任务悬赏分
     * @param title    任务标题
     * @param content  任务内容
     * @param receiver 任务可接受者list，json格式，Content-Type=application/json
     * @param endTime  任务结束时间
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/publishTask", method = RequestMethod.POST)
    public ModelAndView publishTask(@SessionAttribute("user") User user,
                                    @RequestParam("teamId") String teamId,
                                    @RequestParam("reward") int reward,
                                    @RequestParam("title") String title,
                                    @RequestParam("content") String content,
                                    @RequestBody List<String> receiver,
                                    @RequestParam("endTime") String endTime) {
        Task task = new Task();
        task.setId(UUID.randomUUID().toString());
        task.setTeamId(teamId);
        task.setReward(reward);
        task.setTitle(title);
        task.setContent(content);
        task.setEndTime(endTime);
        task.setReceiver(receiver);

        try {
            return taskService.publishTask(task);
        } catch (RuntimeException e) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }

    /**
     * 根据id删除任务，未被接受的任务才能删除
     *
     * @param user session中当前用户的信息
     * @param id   任务id
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/deleteTask", method = RequestMethod.POST)
    public ModelAndView deleteTask(@SessionAttribute("user") User user, @RequestParam("id") String id) {
        try {
            return taskService.deleteTask(user.getId(), id);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }
}
