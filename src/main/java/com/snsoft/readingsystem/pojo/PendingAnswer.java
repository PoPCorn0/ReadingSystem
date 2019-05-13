/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.07
 *
 * @Description
 */

package com.snsoft.readingsystem.pojo;

import java.io.Serializable;

public class PendingAnswer implements Serializable {
    private String id;
    private String taskId;
    private String receivedTaskId;
    private String authorId;
    private String title;
    private String content;
    private String commitTime;
    private String checkTime;
    private char checkMark;
    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getReceivedTaskId() {
        return receivedTaskId;
    }

    public void setReceivedTaskId(String receivedTaskId) {
        this.receivedTaskId = receivedTaskId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
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

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public char getCheckMark() {
        return checkMark;
    }

    public void setCheckMark(char checkMark) {
        this.checkMark = checkMark;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "PendingAnswer{" +
                "id='" + id + '\'' +
                ", taskId='" + taskId + '\'' +
                ", receivedTaskId='" + receivedTaskId + '\'' +
                ", authorId='" + authorId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", commitTime='" + commitTime + '\'' +
                ", checkTime='" + checkTime + '\'' +
                ", checkMark=" + checkMark +
                ", reason='" + reason + '\'' +
                '}';
    }
}
