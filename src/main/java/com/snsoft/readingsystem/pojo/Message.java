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

public class Message implements Serializable {
    private String id;
    private String targetId;
    private String content;
    private String sendTime;
    private char isRead;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public char getIsRead() {
        return isRead;
    }

    public void setIsRead(char isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", targetId='" + targetId + '\'' +
                ", content='" + content + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", isRead=" + isRead +
                '}';
    }
}
