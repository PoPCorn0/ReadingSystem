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

package com.snsoft.readingsystem.pojo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class CheckLog implements Serializable {
    private String id;
    private char checkType;
    private String taskOrAnswerId;
    private String authorId;
    private String checker;
    private String checkTime;
    private char isPass;
    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public char getCheckType() {
        return checkType;
    }

    public void setCheckType(char checkType) {
        this.checkType = checkType;
    }

    public String getTaskOrAnswerId() {
        return taskOrAnswerId;
    }

    public void setTaskOrAnswerId(String taskOrAnswerId) {
        this.taskOrAnswerId = taskOrAnswerId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public char getIsPass() {
        return isPass;
    }

    public void setIsPass(char isPass) {
        this.isPass = isPass;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return JSON.toJSONString((this));
    }
}
