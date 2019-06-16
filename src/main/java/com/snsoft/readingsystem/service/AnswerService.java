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
import com.snsoft.readingsystem.pojo.Answer;
import com.snsoft.readingsystem.pojo.Attachment;
import com.snsoft.readingsystem.pojo.PayRecord;
import com.snsoft.readingsystem.pojo.Task;
import com.snsoft.readingsystem.returnPojo.AnswerDetailInfo;
import com.snsoft.readingsystem.utils.AllConstant;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import com.snsoft.readingsystem.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class AnswerService {
    @Resource
    AnswerDao answerDao;
    @Resource
    TaskDao taskDao;
    @Resource
    AttachmentDao attachmentDao;
    @Resource
    RecordDao recordDao;
    @Resource
    UserDao userDao;

    @Transactional
    public ModelAndView getAnswerDetail(User user, String id) {
        // 判断传入的解读id是否存在
        Answer answer = answerDao.getAnswerById(id);
        if (answer == null) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL, "解读不存在");
        }

        // 判断解读对应的任务信息是否存在
        Task task = taskDao.getTaskById(answer.getTaskId());
        if (task == null) {
            return ModelAndViewUtil.getModelAndView(Code.FAIL);
        }

        // 为返回信息对象添加和任务有关的信息
        AnswerDetailInfo answerDetailInfo = new AnswerDetailInfo();
        answerDetailInfo.setTaskId(task.getId());
        answerDetailInfo.setTeamName(task.getTeamId());
        answerDetailInfo.setTaskReward(task.getReward());
        answerDetailInfo.setTaskTitle(task.getTitle());
        answerDetailInfo.setTaskContent(task.getContent());
        Attachment taskAttachment = attachmentDao.getAttachmentByRelyOnId(task.getId());
        if (taskAttachment != null) {
            answerDetailInfo.setTaskAttachmentId(taskAttachment.getId());
        }

        // 如果当前用户是学生则先判断是否已经付过费，否则付费并扣除相应积分
        if (user.getIdentityMark() == Identity.TEACHER.getIdentity()) {
            answerDetailInfo.setAnswers(answerDao.getAnswerInfosByReceivedTaskId(answer.getReceivedTaskId()));
        } else {
            PayRecord payRecord = recordDao.getPayRecordByStudentIdAndAnswerId(user.getId(),
                    answer.getId());
            if (payRecord == null) {
                PayRecord newPayRecord = new PayRecord();
                newPayRecord.setId(UUID.randomUUID().toString());
                newPayRecord.setAnswerId(answer.getId());
                newPayRecord.setPayId(user.getId());
                newPayRecord.setReceivedTaskId(answer.getReceivedTaskId());
                recordDao.addPayRecord(newPayRecord);
            }
            userDao.updateScore(user.getId(), task.getTeamId(), AllConstant.PAY_FOR_ANSWER);

            answerDetailInfo.setAnswers(answerDao.getAnswerInfosByReceivedTaskId(answer.getReceivedTaskId()));
        }

        return ModelAndViewUtil.getModelAndView("data", answerDetailInfo);
    }
}
