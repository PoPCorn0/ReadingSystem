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

import com.snsoft.readingsystem.dao.*;
import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.enums.Identity;
import com.snsoft.readingsystem.enums.Msg;
import com.snsoft.readingsystem.pojo.*;
import com.snsoft.readingsystem.utils.AllConstant;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import com.snsoft.readingsystem.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CheckService {
    @Resource
    PendingTaskDao pendingTaskDao;
    @Resource
    TaskDao taskDao;
    @Resource
    UserDao userDao;
    @Resource
    MessageDao messageDao;
    @Resource
    PendingAnswerDao pendingAnswerDao;
    @Resource
    AnswerDao answerDao;
    @Resource
    TeamDao teamDao;

    /**
     * 审核学生提交的待审核任务，审核通过时调用的重载方法
     *
     * @param user          session中用户信息
     * @param pendingTaskId 待审核任务id
     * @param endTime       任务结束时间
     * @param reward        悬赏分
     * @param receiver      可接受者id列表
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView checkTask(User user, String pendingTaskId, String endTime, Integer reward,
                                  List<String> receiver) {
        PendingTask pendingTask = pendingTaskDao.getPendingTaskById(pendingTaskId);

        if (pendingTask == null) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "该任务不存在");
        }

        // 如果当前用户不是导师则判断是不是要审核的任务所在团队的导师助手
        String teamId = pendingTask.getTeamId();
        if (user.getIdentityMark() != Identity.TEACHER.getIdentity()) {
            Team team = teamDao.getTeamById(teamId);
            if (!team.getAssistantId().equals(user.getId())) {
                return ModelAndViewUtil.getModelAndView(Code.FAIL, Msg.PERMISSION_DENIED.getMsg());
            }
        }

        // 如果当前是导师助手判断是否审核自己提交的任务
        if (pendingTask.getAuthorId().equals(user.getId())) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "无法审核自己提交的任务");
        }

        // 判断是否重复审核该任务
        if (pendingTask.getCheckMark() != '0') {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "重复审核");
        }

        // 如果可接受者列表中有任务提交者则将其移除
        receiver.remove(pendingTask.getAuthorId());

        // 添加到正式任务表
        Task task = new Task();
        task.setId(pendingTaskId);
        task.setTeamId(teamId);
        task.setAuthorId(pendingTask.getAuthorId());
        task.setReward(reward);
        task.setTitle(pendingTask.getTitle());
        task.setContent(pendingTask.getContent());
        task.setEndTime(endTime);
        task.setReceiver(receiver);
        if (taskDao.publishTask(task) != 1) return ModelAndViewUtil.getModelAndView(Code.FAIL);

        // 修改待审核任务表信息
        pendingTask.setCheckMark('1');
        pendingTask.setId(pendingTaskId);
        if (pendingTaskDao.updatePendingTask(pendingTask) != 1)
            return ModelAndViewUtil.getModelAndView(Code.FAIL);

        // 为该任务添加可接受者到stu_task表中
        ArrayList<StuTask> stuTasks = new ArrayList<>();
        for (String s : receiver
        ) {
            StuTask stuTask = new StuTask();
            stuTask.setStudentId(s);
            stuTask.setTaskId(task.getId());
        }
        if (taskDao.addReceiver(stuTasks) != 1) return ModelAndViewUtil.getModelAndView(Code.FAIL);

        // 为任务提交者增加相应积分
        if (userDao.updateScore(task.getAuthorId(), task.getTeamId(), AllConstant.PUBLISH_TASK) != 1)
            return ModelAndViewUtil.getModelAndView(Code.FAIL);
        // 为任务提交者发送消息通知
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setTargetId(task.getAuthorId());
        message.setContent("您提交的任务：" + task.getTitle() + " 已通过");
        if (messageDao.sendMessage(message) != 1) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL);
        }

        return ModelAndViewUtil.getModelAndView(Code.SUCCESS);
    }

    /**
     * 审核学生提交的待审核任务，审核不通过时调用的重载方法
     *
     * @param user          session中用户信息
     * @param pendingTaskId 待审核任务id
     * @param reason        不通过理由
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView checkTask(User user, String pendingTaskId, String reason) {
        PendingTask pendingTask = pendingTaskDao.getPendingTaskById(pendingTaskId);

        if (pendingTask == null) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "该任务不存在");
        }

        // 如果当前用户不是导师则判断是不是要审核的任务所在团队的导师助手
        String teamId = pendingTask.getTeamId();
        if (user.getIdentityMark() != Identity.TEACHER.getIdentity()) {
            Team team = teamDao.getTeamById(teamId);
            if (!team.getAssistantId().equals(user.getId())) {
                return ModelAndViewUtil.getModelAndView(Code.FAIL, Msg.PERMISSION_DENIED.getMsg());
            }
        }

        // 如果当前是导师助手判断是否审核自己提交的任务
        if (pendingTask.getAuthorId().equals(user.getId())) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "无法审核自己提交的任务");
        }

        // 判断是否重复审核该任务
        if (pendingTask.getCheckMark() != '0') {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "重复审核");
        }

        // 修改待审核任务表信息
        pendingTask.setCheckMark('2');
        pendingTask.setReason(reason);
        if (pendingTaskDao.updatePendingTask(pendingTask) != 1)
            return ModelAndViewUtil.getModelAndView(Code.FAIL);

        // 为任务提交者发送消息通知
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setContent("您提交的任务：" + pendingTask.getTitle() + " 未通过，理由如下：\n" +
                reason);
        message.setTargetId(pendingTask.getAuthorId());
        if (messageDao.sendMessage(message) != 1)
            return ModelAndViewUtil.getModelAndView(Code.FAIL);

        return ModelAndViewUtil.getModelAndView(Code.SUCCESS);
    }

    /**
     * 审核学生提交解读，审核通过时调用的重载方法
     *
     * @param user            当前用户id
     * @param pendingAnswerId 待审核解读id
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView checkAnswer(User user, String pendingAnswerId) {
        PendingAnswer pendingAnswer = pendingAnswerDao.getPendingAnswerById(pendingAnswerId);

        // 如果当前用户不是导师则判断是不是要审核的解读所在团队的导师助手
        if (user.getIdentityMark() != Identity.TEACHER.getIdentity()) {
            Team team = teamDao.getTeamByReceivedTaskId(pendingAnswer.getReceivedTaskId());
            if (team == null) {
                return ModelAndViewUtil.getModelAndView(Code.FAIL);
            }
            if (!team.getAssistantId().equals(user.getId())) {
                return ModelAndViewUtil.getModelAndView(Code.FAIL, Msg.PERMISSION_DENIED.getMsg());
            }
        }

        if (pendingAnswer == null) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "待审核解读不存在");
        }

        if (pendingAnswer.getAuthorId().equals(user.getId())) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "无法审核自己提交的解读");
        }

        // 判断是否重复审核该任务
        if (pendingAnswer.getCheckMark() != '0') {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "重复审核");
        }

        // 添加一条解读&追加解读到answer表中
        String receivedTaskId = pendingAnswer.getReceivedTaskId();
        Task task = taskDao.getTaskByReceivedTaskId(receivedTaskId);
        Answer answer = new Answer();
        answer.setId(pendingAnswer.getId());
        answer.setAuthorId(pendingAnswer.getAuthorId());
        answer.setContent(pendingAnswer.getContent());
        answer.setTitle(pendingAnswer.getTitle());
        answer.setTier(answerDao.getMaxTierByReceivedTaskId(receivedTaskId) + 1);
        answer.setReceivedTaskId(receivedTaskId);
        answer.setTaskId(task.getId());
        if (answerDao.addAnswer(answer) != 1) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL);
        }

        // 为解读提交者增加相应任务悬赏分
        if (userDao.addScore(answer.getAuthorId(), task.getReward()) != 1) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL);
        }

        // 更新待审核解读表中记录
        pendingAnswer.setId(pendingAnswerId);
        pendingAnswer.setCheckMark('1');
        if (pendingAnswerDao.checkAnswer(pendingAnswer) != 1) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL);
        }

        // 发送一条消息通知
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setTargetId(answer.getAuthorId());
        message.setContent("您提交的解读：" + answer.getTitle() + " 已通过审核");
        if (messageDao.sendMessage(message) != 1) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL);
        }

        return ModelAndViewUtil.getModelAndView(Code.SUCCESS);
    }

    /**
     * 审核学生提交解读&追加解读，审核不通过时调用的重载方法
     *
     * @param user            当前用户id
     * @param pendingAnswerId 待审核解读id
     * @param reason          不通过理由
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView checkAnswer(User user, String pendingAnswerId, String reason) {
        PendingAnswer pendingAnswer = pendingAnswerDao.getPendingAnswerById(pendingAnswerId);

        // 如果当前用户不是导师则判断是不是要审核的解读所在团队的导师助手
        if (user.getIdentityMark() != Identity.TEACHER.getIdentity()) {
            Team team = teamDao.getTeamByReceivedTaskId(pendingAnswer.getReceivedTaskId());
            if (team == null) {
                return ModelAndViewUtil.getModelAndView(Code.FAIL);
            }
            if (!team.getAssistantId().equals(user.getId())) {
                return ModelAndViewUtil.getModelAndView(Code.FAIL, Msg.PERMISSION_DENIED.getMsg());
            }
        }

        if (pendingAnswer == null) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "待审核解读不存在");
        }

        if (pendingAnswer.getAuthorId().equals(user.getId())) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "无法审核自己提交的解读");
        }

        // 判断是否重复审核该任务
        if (pendingAnswer.getCheckMark() != '0') {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "重复审核");
        }

        // 更新追加解读表中记录
        pendingAnswer.setId(pendingAnswerId);
        pendingAnswer.setCheckMark('2');
        pendingAnswer.setReason(reason);
        if (pendingAnswerDao.checkAnswer(pendingAnswer) != 1) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL);
        }

        // 发送一条消息通知
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setTargetId(pendingAnswer.getAuthorId());
        message.setContent("您提交的解读：" + pendingAnswer.getTitle() + " 未通过审核");
        if (messageDao.sendMessage(message) != 1) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL);
        }

        return ModelAndViewUtil.getModelAndView(Code.SUCCESS);
    }
}
