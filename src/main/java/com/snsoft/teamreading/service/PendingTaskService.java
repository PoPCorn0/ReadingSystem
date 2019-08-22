/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.08.22
 *
 * @Description
 */

package com.snsoft.teamreading.service;

import com.snsoft.teamreading.dao.AttachmentDao;
import com.snsoft.teamreading.dao.PendingTaskDao;
import com.snsoft.teamreading.dao.TeamDao;
import com.snsoft.teamreading.enums.Code;
import com.snsoft.teamreading.pojo.Attachment;
import com.snsoft.teamreading.pojo.PendingTask;
import com.snsoft.teamreading.pojo.Team;
import com.snsoft.teamreading.utils.ModelAndViewUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;

@Service
public class PendingTaskService {
    @Resource
    PendingTaskDao pendingTaskDao;
    @Resource
    TeamDao teamDao;
    @Resource
    AttachmentDao attachmentDao;
    @Resource
    ModelAndView mv;

    @Transactional
    public ModelAndView commitTask(PendingTask pendingTask) {
        String teamId = pendingTask.getTeamId();
        Team team = teamDao.getTeamById(teamId);

        if (team == null) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "该团队不存在");
        }

        if (teamDao.getTeamStuByTeamIdAndStudentId(teamId, pendingTask.getAuthorId()) == null) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "您不属于该团队");
        }

        return pendingTaskDao.commitTask(pendingTask) == 1 ?
                ModelAndViewUtil.addObject(mv, Code.SUCCESS) :
                ModelAndViewUtil.addObject(mv, Code.FAIL);
    }

    public ModelAndView deletePendingTask(String userId, String pendingTaskId) {
        PendingTask pendingTask = pendingTaskDao.getPendingTaskById(pendingTaskId);
        if (pendingTask == null) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "该待审核任务不存在");
        }

        if (!pendingTask.getAuthorId().equals(userId)) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "无法操作不属于自己的待审核任务");
        }

        if (pendingTask.getCheckMark() == '1') {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "无法删除已通过审核的任务");
        }

        // 删除磁盘中文件，并从附件表中删除记录
        Attachment attachment = attachmentDao.getAttachmentByRelyOnId(pendingTaskId);
        if (attachment != null) {
            File file = new File(attachment.getSavePath());
            if (!file.delete()) {
                return ModelAndViewUtil.addObject(mv, Code.FAIL, "任务附件删除失败");
            }
            attachmentDao.deleteAttachmentByRelyOnId(pendingTaskId);
        }

        return pendingTaskDao.deletePendingTaskById(pendingTaskId) == 1 ?
                ModelAndViewUtil.addObject(mv, Code.SUCCESS) :
                ModelAndViewUtil.addObject(mv, Code.FAIL);
    }
}
