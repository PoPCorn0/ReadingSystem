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

import com.snsoft.readingsystem.dao.TeamDao;
import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.enums.Identity;
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
import java.util.List;

@Controller("CommonTeamController")
@RequestMapping("/common")
public class TeamController {

    @Resource
    TeamService teamService;
    @Resource
    TeamDao teamDao;
    @Resource
    ModelAndView mv;

    /**
     * 当前用户是学生时返回学生所在的所有团队信息，为导师时返回导师创建的所有团队信息
     *
     * @param user session中用户信息
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/getTeam", method = RequestMethod.GET)
    public ModelAndView getTeam(@SessionAttribute("user") User user) {
        try {
            if (user.getIdentityMark() == Identity.STUDENT.getIdentity()) {
                List<Team> teams = teamDao.getTeamsByStudentId(user.getId());
                if (teams == null) {
                    return ModelAndViewUtil.addObject(mv, Code.FAIL);
                }
                return ModelAndViewUtil.addObject(mv, "data", teams);
            } else {
                List<Team> teams = teamDao.getTeamsByTeacherId(user.getId());
                if (teams == null) {
                    return ModelAndViewUtil.addObject(mv, Code.FAIL);
                }
                return ModelAndViewUtil.addObject(mv, "data", teams);
            }
        } catch (RuntimeException e) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
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
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }
}
