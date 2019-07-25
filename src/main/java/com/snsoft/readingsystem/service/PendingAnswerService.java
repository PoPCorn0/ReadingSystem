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

package com.snsoft.readingsystem.service;

import com.snsoft.readingsystem.dao.PendingAnswerDao;
import com.snsoft.readingsystem.dao.ReceivedTaskDao;
import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.pojo.PendingAnswer;
import com.snsoft.readingsystem.pojo.ReceivedTask;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Service
public class PendingAnswerService {
    @Resource
    ReceivedTaskDao receivedTaskDao;
    @Resource
    PendingAnswerDao pendingAnswerDao;
    @Resource
    ModelAndView mv;

    /**
     * 添加待审核解读
     *
     * @param pendingAnswer 待审核解读对象
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView commitAnswer(PendingAnswer pendingAnswer) {
        String receivedTaskId = pendingAnswer.getReceivedTaskId();
        ReceivedTask receivedTask = receivedTaskDao.getReceivedTaskByIdNotFinal(receivedTaskId);

        if (receivedTask == null || !receivedTask.getReceiverId().equals(pendingAnswer.getAuthorId())) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "未接受该任务");
        }

        if (pendingAnswerDao.getPendingAnswerByReceivedTaskId(pendingAnswer.getReceivedTaskId()) != null) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "无法重复提交");
        }

        return pendingAnswerDao.addPendingAnswer(pendingAnswer) == 1 ?
                ModelAndViewUtil.addObject(mv, Code.SUCCESS) :
                ModelAndViewUtil.addObject(mv, Code.FAIL);
    }

    /**
     * 删除待审核解读
     *
     * @param userId          用户id
     * @param pendingAnswerId 待审核解读id
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView deletePendingAnswer(String userId, String pendingAnswerId) {
        PendingAnswer pendingAnswer = pendingAnswerDao.getPendingAnswerById(pendingAnswerId);
        if (pendingAnswer == null) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "该待审核解读不存在");
        }

        if (!pendingAnswer.getAuthorId().equals(userId)) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "无法删除不属于自己的待审核解读");
        }

        if (pendingAnswer.getCheckMark() != '0') {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "无法删除已通过审核的解读");
        }

        return pendingAnswerDao.deletePendingAnswer(pendingAnswerId) == 1 ?
                ModelAndViewUtil.addObject(mv, Code.SUCCESS) :
                ModelAndViewUtil.addObject(mv, Code.FAIL);
    }
}
