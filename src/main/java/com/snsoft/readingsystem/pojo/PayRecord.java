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

public class PayRecord implements Serializable {
    private String id;
    private String payId;
    private String answerId;
    private String receivedTaskId;
    private String payTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getReceivedTaskId() {
        return receivedTaskId;
    }

    public void setReceivedTaskId(String receivedTaskId) {
        this.receivedTaskId = receivedTaskId;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString((this));
    }
}
