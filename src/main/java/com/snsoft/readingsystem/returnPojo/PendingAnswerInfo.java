/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.14
 *
 * @Description
 */

package com.snsoft.readingsystem.returnPojo;

import java.io.Serializable;

public class PendingAnswerInfo implements Serializable {
    private String id;
    private String receivedTaskId;
    private String title;
    private String content;
    private String commitTime;
    private String checkTime;
    private int praiseAmount;
    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceivedTaskId() {
        return receivedTaskId;
    }

    public void setReceivedTaskId(String receivedTaskId) {
        this.receivedTaskId = receivedTaskId;
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

    public int getPraiseAmount() {
        return praiseAmount;
    }

    public void setPraiseAmount(int praiseAmount) {
        this.praiseAmount = praiseAmount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "PendingAnswerInfo{" +
                "id='" + id + '\'' +
                ", receivedTaskId='" + receivedTaskId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", commitTime='" + commitTime + '\'' +
                ", checkTime='" + checkTime + '\'' +
                ", praiseAmount=" + praiseAmount +
                ", reason='" + reason + '\'' +
                '}';
    }
}
