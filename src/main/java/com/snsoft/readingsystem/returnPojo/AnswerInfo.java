/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.19
 *
 * @Description
 */

package com.snsoft.readingsystem.returnPojo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class AnswerInfo implements Serializable {
    private String id;
    private String receivedTaskInfo;
    private String authorId;
    private String authorName;
    private int tier;
    private String title;
    private String content;
    private String teamName;
    private String attachmentId;
    private String commitTime;
    private int praiseAmount;
    private char isPaid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getReceivedTaskInfo() {
        return receivedTaskInfo;
    }

    public void setReceivedTaskInfo(String receivedTaskInfo) {
        this.receivedTaskInfo = receivedTaskInfo;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
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

    public char getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(char isPaid) {
        this.isPaid = isPaid;
    }

    @Override
    public String toString() {
        return JSON.toJSONString((this));
    }
}
