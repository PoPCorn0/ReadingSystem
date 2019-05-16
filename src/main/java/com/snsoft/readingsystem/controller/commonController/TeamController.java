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

import com.snsoft.readingsystem.dao.TeamDao;
import com.snsoft.readingsystem.pojo.Student;
import com.snsoft.readingsystem.pojo.Team;
import com.snsoft.readingsystem.pojo.TeamStu;
import com.snsoft.readingsystem.returnPojo.StudentInfo;
import com.snsoft.readingsystem.service.TeamService;
import com.snsoft.readingsystem.utils.AllConstant;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import com.snsoft.readingsystem.utils.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller("Common_TeamController")
@RequestMapping("/common")
public class TeamController {

    @Resource
    TeamService teamService;
    @Resource
    TeamDao teamDao;

    /**
     * 当前用户是学生时返回学生所在的所有团队信息，为导师时返回导师创建的所有团队信息
     *
     * @param user session中用户信息
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/getTeam", method = RequestMethod.GET)
    public ModelAndView getTeam(@SessionAttribute("user") User user) {
        try {
            if (user.getIdentityMark() == AllConstant.IDENTITYMARK_STUDENT) {
                List<Team> teams = teamDao.getTeamByStudentId(user.getId());
                if (teams == null) {
                    return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
                }
                return ModelAndViewUtil.getModelAndView("data", teams);
            } else {
                List<Team> teams = teamDao.getTeamByTeacherId(user.getId());
                if (teams == null) {
                    return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
                }
                return ModelAndViewUtil.getModelAndView("data", teams);
            }
        } catch (RuntimeException e) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_ERROR);
        }
    }

    /**
     * 根据团队id获取所有学生信息
     *
     * @param teamId 团队id
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/getStudentByTeamId", method = RequestMethod.GET)
    public ModelAndView getStudentByTeamId(@RequestParam("id") String teamId) {
        try {
            return teamService.getStudentsByTeamId(teamId);
        } catch (RuntimeException e) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_ERROR);
        }
    }
}
