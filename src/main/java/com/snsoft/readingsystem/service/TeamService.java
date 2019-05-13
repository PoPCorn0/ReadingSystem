/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.09
 *
 * @Description
 */

package com.snsoft.readingsystem.service;

import com.snsoft.readingsystem.dao.ReceivedTaskDao;
import com.snsoft.readingsystem.dao.TaskDao;
import com.snsoft.readingsystem.dao.TeamDao;
import com.snsoft.readingsystem.dao.UserDao;
import com.snsoft.readingsystem.pojo.Student;
import com.snsoft.readingsystem.pojo.Team;
import com.snsoft.readingsystem.pojo.TeamStu;
import com.snsoft.readingsystem.utils.AllConstant;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Service
public class TeamService {
    @Resource
    TeamDao teamDao;
    @Resource
    UserDao userDao;
    @Resource
    TaskDao taskDao;
    @Resource
    ReceivedTaskDao receivedTaskDao;

    /**
     * 删除团队，不可恢复
     *
     * @param teacherId 当前导师用户id
     * @param teamId    要删除的团队id
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView deleteTeam(String teacherId, String teamId) {
        Team team = teamDao.getTeamById(teamId);
        if (team == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "该团队不存在");
        }

        if (!team.getTeacherId().equals(teacherId)) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "无法操作不属于自己的团队");
        }

        teamDao.deleteTeamStuByTeamId(teamId);
        taskDao.setTaskFinalByTeamId(teamId);
        receivedTaskDao.setReceivedTaskFinalByTeamId(teamId);

        //删除团队
        return teamDao.deleteTeam(teamId) == 1 ?
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS) :
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
    }

    /**
     * 添加学生到团队
     *
     * @param teamId    团队id
     * @param studentId 学生id
     * @param teacherId 导师id
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView addStudentToTeam(String teamId, String studentId, String teacherId) {
        Team team = teamDao.getTeamById(teamId);
        Student student = userDao.getStudentByIdNotRemoved(studentId);

        //判断团队是否存在
        if (team == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "该团队不存在");
        }

        //判断学生是否存在
        if (student == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "该学生不存在");
        }

        //判断该团队是否由该用户创建
        if (!team.getTeacherId().equals(teacherId)) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "无法操作不属于自己的团队");
        }

        //判断学生是否在团队中
        TeamStu teamStu = teamDao.getTeamStuByTeamIdAndStudentId(teamId, studentId);
        if (teamStu != null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "该学生已经在团队中");
        }

        teamStu = teamDao.getRemovedTeamStu(teamId, studentId);
        if (teamStu != null) {
            //如果该学生被移除过则恢复删除时的操作
            taskDao.updateTaskByTeamIdAndStudentId(teamId, studentId, '0');
            receivedTaskDao.updateReceivedTaskByTeamIdAndStudentId(teamId, studentId, '0');
            return teamDao.updateTeamStu(teamId, studentId, '0') == 1 ?
                    ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS) :
                    ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
        } else return teamDao.addSToTeam(teamId, studentId) == 1 ?
                //将学生添加到团队中
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS) :
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
    }

    /**
     * 将学生从团队中移除，可以被恢复
     *
     * @param teacherId 当前导师用户id
     * @param teamId    团队id
     * @param studentId 学生id
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView removeStudentFromTeam(String teacherId, String teamId, String studentId) {
        Team team = teamDao.getTeamById(teamId);
        Student student = userDao.getStudentByIdNotRemoved(studentId);

        //判断学生是否存在
        if (student == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "该学生不存在");
        }

        //判断团队是否存在
        if (team == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "该团队不存在");
        }

        //判断该团队是否由该用户创建
        if (!team.getTeacherId().equals(teacherId)) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "无法操作不属于自己的团队");
        }

        //判断学生是否在团队中
        TeamStu teamStu = teamDao.getTeamStuByTeamIdAndStudentId(teamId, studentId);
        if (teamStu == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "该学生不在团队中");
        }

        taskDao.updateTaskByTeamIdAndStudentId(teamId, studentId, '1');
        receivedTaskDao.updateReceivedTaskByTeamIdAndStudentId(teamId, studentId, '1');

        //从团队中移除该学生
        return teamDao.updateTeamStu(teamId, studentId, '1') == 1 ?
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS) :
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
    }
}
