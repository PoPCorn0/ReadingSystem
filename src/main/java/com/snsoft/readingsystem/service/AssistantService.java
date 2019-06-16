/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.06.16
 *
 * @Description
 */

package com.snsoft.readingsystem.service;

import com.snsoft.readingsystem.dao.MessageDao;
import com.snsoft.readingsystem.dao.TeamDao;
import com.snsoft.readingsystem.dao.UserDao;
import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.pojo.Message;
import com.snsoft.readingsystem.pojo.Student;
import com.snsoft.readingsystem.pojo.Team;
import com.snsoft.readingsystem.pojo.TeamStu;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class AssistantService {

    @Resource
    UserDao userDao;
    @Resource
    TeamDao teamDao;
    @Resource
    MessageDao messageDao;

    @Transactional
    public ModelAndView setAssistant(String teacherId, String studentId, String teamId) {
        Student student = userDao.getStudentByIdNotRemoved(studentId);
        Team team = teamDao.getTeamById(teamId);

        //判断学生是否存在
        if (student == null) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "该学生不存在");
        }

        //判断团队是否存在
        if (team == null) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "该团队不存在");
        }

        //判断团队是否由该用户创建
        if (!team.getTeacherId().equals(teacherId)) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "无法操作不属于自己的团队");
        }

        //判断学生是否在该团队中
        TeamStu teamStu = teamDao.getTeamStuByTeamIdAndStudentId(studentId, teamId);
        if (teamStu == null) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "该学生不在该团队中");
        }

        Message messageToOld = new Message();
        Message messageToNew = new Message();
        //如果团队的导师助手不是该学生则指定该学生为导师助手，并发送消息通知
        String oldAssistantId = team.getAssistantId();
        if (oldAssistantId == null || !oldAssistantId.equals(studentId)) {
            team.setAssistantId(studentId);
            if (oldAssistantId != null) {
                messageToOld.setId(UUID.randomUUID().toString());
                messageToOld.setTargetId(oldAssistantId);
                messageToOld.setContent("您不再是团队： " + team.getName() + " 的导师助手");
                messageDao.sendMessage(messageToOld);
            }
            messageToNew.setId(UUID.randomUUID().toString());
            messageToNew.setTargetId(studentId);
            messageToNew.setContent("您被选为团队： " + team.getName() + " 的导师助手");
            messageDao.sendMessage(messageToNew);
            //如果当前的导师助手为该学生则取消，并发送一条消息通知
        } else {
            team.setAssistantId(null);
            messageToOld.setId(UUID.randomUUID().toString());
            messageToOld.setTargetId(studentId);
            messageToOld.setContent("您不再是团队： " + team.getName() + " 的导师助手");
            messageDao.sendMessage(messageToOld);
        }

        //更新团队表
        return teamDao.updateAssistant(team) == 1 ?
                ModelAndViewUtil.getModelAndView(Code.SUCCESS) :
                ModelAndViewUtil.getModelAndView(Code.ERROR);
    }

}
