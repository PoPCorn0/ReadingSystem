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
import com.snsoft.readingsystem.pojo.*;
import com.snsoft.readingsystem.returnPojo.AcceptedTaskInfo;
import com.snsoft.readingsystem.utils.AllConstant;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    @Resource
    TaskDao taskDao;
    @Resource
    TeamDao teamDao;
    @Resource
    ReceivedTaskDao receivedTaskDao;
    @Resource
    UserDao userDao;
    @Resource
    AttachmentDao attachmentDao;
    @Resource
    PendingAnswerDao pendingAnswerDao;

    /**
     * 发布任务
     *
     * @param task 任务对象
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView publishTask(Task task) {
        Team team = teamDao.getTeamById(task.getTeamId());

        //判断团队是否存在
        if (team == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "该团队不存在");
        }

        //判断该团队是否由该用户创建
        if (!team.getTeacherId().equals(task.getAuthorId())) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "无法操作不属于自己的团队");
        }

        //添加到任务表
        if (taskDao.publishTask(task) != 1) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
        }

        List<String> receiver = task.getReceiver();
        ArrayList<StuTask> stuTaskList = new ArrayList<>();
        for (String s : receiver) {
            Student student = userDao.getStudentByIdNotRemoved(s);
            if (student == null) {
                return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "学生id：" + s + "不存在");
            }

            StuTask stuTask = new StuTask();
            stuTask.setTaskId(task.getId());
            stuTask.setStudentId(s);
            stuTaskList.add(stuTask);
        }

        //添加可接受者
        return taskDao.addReceiver(stuTaskList) >= 1 ? ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS) :
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
    }

    /**
     * 删除已发布任务
     *
     * @param teacherId 当前导师用户id
     * @param taskId    任务id
     * @return ModelAndView试图用
     */
    @Transactional
    public ModelAndView deleteTask(String teacherId, String taskId) {
        Task task = taskDao.getAcceptableTaskById(taskId);
        //判断该任务是否存在
        if (task == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "该任务不存在");
        }

        //判断是否是属于自己团队的任务
        String teamId = task.getTeamId();
        Team team = teamDao.getTeamById(teamId);
        if (!team.getTeacherId().equals(teacherId)) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "无法操作不属于自己的团队");
        }

        //判断该任务是否已经有人接受
        List<ReceivedTask> receivedTasks = receivedTaskDao.getReceivedTasksByTaskIdNotFinal(taskId);
        if (receivedTasks != null && receivedTasks.size() != 0) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "无法删除已被接受的任务");
        }

        //从学生-任务表中删除属于该任务的记录
        taskDao.deleteReceiverByTaskIdAndTeamId(taskId);

        //删除磁盘中任务附件并删除表中记录
        Attachment attachment = attachmentDao.getAttachmentByRelyOnId(taskId);
        if (attachment != null) {
            File file = new File(attachment.getSavePath());
            if (!file.delete()) {
                return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "文件删除失败");
            }
            attachmentDao.deleteAttachmentByRelyOnId(taskId);
        }

        //删除可接受任务记录及任务
        return taskDao.deleteTask(taskId) == 1 ?
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS) :
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
    }

    public ModelAndView receiveTask(String userId, String taskId) {
        Task acceptableTask = taskDao.getAcceptableTaskById(taskId);

        if (acceptableTask == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "该任务不存在");
        }

        StuTask stuTask = taskDao.getStuTaskByTaskIdAndStudentId(taskId, userId);
        if (stuTask == null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "无法接受该任务");
        }

        ReceivedTask receivedTask = receivedTaskDao.getReceivedTaskByStudentIdAndTaskId(taskId, userId);
        if (receivedTask != null) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "重复接受任务");
        }

        receivedTask = new ReceivedTask();
        receivedTask.setId(UUID.randomUUID().toString());
        receivedTask.setReceiverId(userId);
        receivedTask.setTaskId(taskId);

        return receivedTaskDao.addReceivedTask(receivedTask) == 1 ?
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS) :
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
    }

    /**
     * 获取所有已接受可提交解读的任务
     *
     * @param userId    用户id
     * @param rowBounds 分页参数
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView getAcceptedTasksNotFinal(String userId, RowBounds rowBounds) {
        // 获取除了提交解读时间commit_time、审核标记check_mark以外的数据
        List<AcceptedTaskInfo> acceptedTaskInfos = taskDao.getAcceptedTasksInfoByStudentId(userId, rowBounds);

        if (acceptedTaskInfos == null || acceptedTaskInfos.size() == 0) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS);
        } else {
            // 为每个AcceptedTaskInfo添加提交解读时间、审核标记（如果提交过解读）
            PendingAnswer pendingAnswer;
            for (AcceptedTaskInfo at : acceptedTaskInfos) {
                pendingAnswer =
                        pendingAnswerDao.getPendingAnswerByReceivedTaskId(at.getReceivedTaskId());
                if (pendingAnswer != null) {
                    at.setCommitTime(pendingAnswer.getCommitTime());
                    at.setCheckMark(pendingAnswer.getCheckMark());
                }
            }
        }

        return ModelAndViewUtil.getModelAndView("data", acceptedTaskInfos);
    }

    /**
     * 删除已接受任务
     *
     * @param userId         用户id
     * @param receivedTaskId 已接受任务id
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView deleteAcceptedTask(String userId, String receivedTaskId) {
        // 根据id已接受任务id查询已接受任务
        ReceivedTask receivedTask = receivedTaskDao.getReceivedTaskByIdNotFinal(receivedTaskId);

        // 判断该已接受任务是否存在和是否属于该用户
        if (receivedTask == null || !receivedTask.getReceiverId().equals(userId)) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "未接受该任务");
        }

        // 删除已接受任务
        return receivedTaskDao.deleteReceivedTaskByStudentIdAndReceivedTaskId(receivedTaskId) == 1 ?
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS) :
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
    }
}
