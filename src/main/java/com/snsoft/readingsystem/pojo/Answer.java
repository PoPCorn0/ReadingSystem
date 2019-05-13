
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

public class Answer implements Serializable {
    private String id;
    private String taskId;
    private String receivedTaskId;
    private String authorId;
    private int tier;
    private String title;
    private String content;
    private String commitTime;
    private int praiseAmount;

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

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
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

    public int getPraiseAmount() {
        return praiseAmount;
    }

    public void setPraiseAmount(int praiseAmount) {
        this.praiseAmount = praiseAmount;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id='" + id + '\'' +
                ", taskId='" + taskId + '\'' +
                ", receivedTaskId='" + receivedTaskId + '\'' +
                ", authorId='" + authorId + '\'' +
                ", tier=" + tier +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", commitTime='" + commitTime + '\'' +
                ", praiseAmount=" + praiseAmount +
                '}';
    }
}
