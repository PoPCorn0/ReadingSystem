/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.21
 *
 * @Description
 */

package com.snsoft.readingsystem.service;

import com.snsoft.readingsystem.dao.*;
import com.snsoft.readingsystem.pojo.Student;
import com.snsoft.readingsystem.pojo.Team;
import com.snsoft.readingsystem.pojo.TeamStu;
import com.snsoft.readingsystem.returnPojo.StudentInfo;
import com.snsoft.readingsystem.utils.AllConstant;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    PendingAnswerDao pendingAnswerDao;
    @Resource
    PendingTaskDao pendingTaskDao;


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
        pendingTaskDao.setPendingTaskDisapproved(teamId, null);
        pendingAnswerDao.setPendingAnswerDisapproved(teamId, null);
        taskDao.deleteStuTaskByTeamId(teamId);

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
        pendingAnswerDao.setPendingAnswerDisapproved(teamId, studentId);
        pendingTaskDao.setPendingTaskDisapproved(teamId, studentId);

        // 如果要删除的学生为导师助手则取消
        if (team.getAssistantId().equals(studentId)) {
            team.setAssistantId(null);
            teamDao.updateAssistant(team);
        }

        //从团队中移除该学生
        return teamDao.updateTeamStu(teamId, studentId, '1') == 1 ?
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS) :
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
    }

    /**
     * 添加团队
     *
     * @param team 团队对象
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView addTeam(Team team) {
        if (teamDao.addTeam(team) != 1) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
        }

        if (teamDao.addScoreStandard(team.getId()) != 1) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
        }

        return ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS);
    }

    /**
     * 根据团队id 获取所有学生信息
     *
     * @param teamId 团队id
     * @return ModelAndView视图
     */
    public ModelAndView getStudentsByTeamId(String teamId) {
        Team team = teamDao.getTeamById(teamId);

        if (team == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "该团队不存在");
        }

        List<StudentInfo> students = teamDao.getStudentsByTeamId(teamId);
        if (students == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
        }

        String assistantId = team.getAssistantId();

        for (StudentInfo student : students
        ) {
            if (student.getId().equals(assistantId)) {
                student.setIsAssistant('1');
            } else student.setIsAssistant('0');
        }

        return ModelAndViewUtil.getModelAndView("data", students);
    }
}
