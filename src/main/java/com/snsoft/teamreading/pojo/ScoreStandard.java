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

package com.snsoft.teamreading.pojo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class ScoreStandard implements Serializable {
    private String teamId;
    private int publishTask;
    private int praise;
    private int payForAnswer;
    private int intialScore;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public int getPublishTask() {
        return publishTask;
    }

    public void setPublishTask(int publishTask) {
        this.publishTask = publishTask;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public int getPayForAnswer() {
        return payForAnswer;
    }

    public void setPayForAnswer(int payForAnswer) {
        this.payForAnswer = payForAnswer;
    }

    public int getIntialScore() {
        return intialScore;
    }

    public void setIntialScore(int intialScore) {
        this.intialScore = intialScore;
    }

    @Override
    public String toString() {
        return JSON.toJSONString((this));
    }
}
