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

package com.snsoft.teamreading.returnPojo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

public class AnswerDetailInfo implements Serializable {
    private String taskId;
    private String teamName;
    private int taskReward;
    private String taskTitle;
    private String taskContent;
    private String taskAttachmentId;
    private List<AnswerInfo> answers;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTaskReward() {
        return taskReward;
    }

    public void setTaskReward(int taskReward) {
        this.taskReward = taskReward;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTaskAttachmentId() {
        return taskAttachmentId;
    }

    public void setTaskAttachmentId(String taskAttachmentId) {
        this.taskAttachmentId = taskAttachmentId;
    }

    public List<AnswerInfo> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerInfo> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return JSON.toJSONString((this));
    }
}
