/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.08.22
 *
 * @Description
 */

package com.snsoft.teamreading.controller.commonController;

import com.snsoft.teamreading.dao.TaskDao;
import com.snsoft.teamreading.enums.Code;
import com.snsoft.teamreading.enums.Identity;
import com.snsoft.teamreading.pojo.User;
import com.snsoft.teamreading.utils.ModelAndViewUtil;
import com.snsoft.teamreading.utils.RowBoundsUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller("CommonTaskController")
@RequestMapping("/common")
public class TaskController {
    @Resource
    TaskDao taskDao;
    @Resource
    ModelAndView mv;

    /**
     * 如果当前是导师账号则获取其创建的所有团队的已发布任务，如果是学生账号则获取其所有可以接受的已发布任务
     *
     * @param user session中用户信息
     * @param page 分页参数
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/getTask", method = RequestMethod.GET)
    public ModelAndView getTask(@SessionAttribute("user") User user,
                                @RequestParam(value = "page", required = false) Integer page) {
        RowBounds rowBounds = RowBoundsUtil.getRowBounds(page);

        try {
            if (user.getIdentityMark() == Identity.TEACHER.getIdentity()) {
                return ModelAndViewUtil.addObject(mv, "data", taskDao.getTeacherTaskInfo(user.getId(), rowBounds));
            } else {
                return ModelAndViewUtil.addObject(mv, "data", taskDao.getStudentTaskInfo(user.getId(), rowBounds));
            }
        } catch (RuntimeException e) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }

    /**
     * 查看任务详情，根据不同类型的任务返回不同的数据（待审核任务、正式任务）
     *
     * @param id 待审核任务id或任务id
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/getTaskDetail", method = RequestMethod.GET)
    public ModelAndView getTaskDetail(@RequestParam("id") String id) {
        try {
            return ModelAndViewUtil.addObject(mv, "data", taskDao.getTaskDetail(id));
        } catch (RuntimeException e) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }
}
