/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.13
 *
 * @Description
 */

package com.snsoft.readingsystem.returnPojo;

import java.io.Serializable;

public class AcceptedTaskInfo implements Serializable {
    private String receivedTaskId;
    private String taskId;
    private String teamId;
    private int reward;
    private String title;
    private String content;
    private String attachmentId;
    private String receiveTime;
    private String commitTime;
    private char checkMark;

    public String getReceivedTaskId() {
        return receivedTaskId;
    }

    public void setReceivedTaskId(String receivedTaskId) {
        this.receivedTaskId = receivedTaskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public char getCheckMark() {
        return checkMark;
    }

    public void setCheckMark(char checkMark) {
        this.checkMark = checkMark;
    }

    @Override
    public String toString() {
        return "AcceptedTaskInfo{" +
                "receivedTaskId='" + receivedTaskId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", teamId='" + teamId + '\'' +
                ", reward=" + reward +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", attachmentId='" + attachmentId + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                ", commitTime='" + commitTime + '\'' +
                ", checkMark=" + checkMark +
                '}';
    }
}
