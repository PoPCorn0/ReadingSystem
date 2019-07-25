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

import com.snsoft.readingsystem.dao.TeamDao;
import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.pojo.Team;
import com.snsoft.readingsystem.pojo.User;
import com.snsoft.readingsystem.service.TeamService;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.UUID;

@Controller("TeacherTeamController")
@RequestMapping("teacher")
public class TeamController {
    @Resource
    TeamService teamService;
    @Resource
    TeamDao teamDao;
    @Resource
    ModelAndView mv;

    /**
     * 导师接口，创建团队
     *
     * @param user session中用户信息
     * @param name 团队名
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/addTeam", method = RequestMethod.POST)
    public ModelAndView addTeam(@SessionAttribute("user") User user, @RequestParam("name") String name) {
        Team team = new Team();
        team.setId(UUID.randomUUID().toString());
        team.setName(name);
        team.setTeacherId(user.getId());

        try {
            return teamService.addTeam(team);
        } catch (RuntimeException e) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }

    /**
     * 导师接口，删除团队
     *
     * @param id 要删除的团队id
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/deleteTeam", method = RequestMethod.POST)
    public ModelAndView deleteTeam(@SessionAttribute("user") User user, @RequestParam("id") String id) {
        try {
            return teamService.deleteTeam(user.getId(), id);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }

    /**
     * 导师接口，添加学生到团队中
     *
     * @param user      session中用户信息
     * @param teamId    要添加学生到的团队id
     * @param studentId 要添加的学生的id
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/addToTeam", method = RequestMethod.POST)
    public ModelAndView addToTeam(@SessionAttribute("user") User user, @RequestParam("teamId") String teamId,
                                  @RequestParam("studentId") String studentId) {
        try {
            return teamService.addStudentToTeam(teamId, studentId, user.getId());
        } catch (RuntimeException e) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }

    /**
     * 导师接口，将学生从团队中移除
     *
     * @param teamId    要删除学生所在的团队id
     * @param studentId 要删除的学生的id
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/removeFromTeam", method = RequestMethod.POST)
    public ModelAndView removeFromTeam(@SessionAttribute("user") User user,
                                       @RequestParam("teamId") String teamId,
                                       @RequestParam("studentId") String studentId) {
        try {
            return teamService.removeStudentFromTeam(user.getId(), teamId, studentId);
        } catch (RuntimeException e) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }
}
