/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.12
 *
 * @Description
 */

package com.snsoft.readingsystem.service;

import com.snsoft.readingsystem.dao.AttachmentDao;
import com.snsoft.readingsystem.dao.PendingTaskDao;
import com.snsoft.readingsystem.dao.TeamDao;
import com.snsoft.readingsystem.pojo.PendingTask;
import com.snsoft.readingsystem.pojo.Team;
import com.snsoft.readingsystem.utils.AllConstant;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Service
public class PendingTaskService {
    @Resource
    PendingTaskDao pendingTaskDao;
    @Resource
    TeamDao teamDao;
    @Resource
    AttachmentDao attachmentDao;

    @Transactional
    public ModelAndView commitTask(PendingTask pendingTask) {
        String teamId = pendingTask.getTeamId();
        Team team = teamDao.getTeamById(teamId);

        if (team == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "该团队不存在");
        }

        if (teamDao.getTeamStuByTeamIdAndStudentId(teamId, pendingTask.getAuthorId()) == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "您不属于该团队");
        }

        return pendingTaskDao.commitTask(pendingTask) == 1 ?
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS) :
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
    }

    public ModelAndView deletePendingTask(String userId, String pendingTaskId) {
        PendingTask pendingTask = pendingTaskDao.getPendingTaskById(pendingTaskId);
        if (pendingTask == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "该待审核任务不存在");
        }

        if (!pendingTask.getAuthorId().equals(userId)) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "无法操作不属于自己的待审核任务");
        }

        if (pendingTask.getCheckMark() == '1') {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "无法删除已通过审核的任务");
        }

        attachmentDao.deleteAttachment(pendingTask.getId());
        // TODO 删除任务文件

        return pendingTaskDao.deletePendingTaskById(pendingTaskId) == 1 ?
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS) :
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
    }
}
