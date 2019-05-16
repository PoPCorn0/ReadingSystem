/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.15
 *
 * @Description
 */

package com.snsoft.readingsystem.returnPojo;

import java.io.Serializable;

public class StudentInfo implements Serializable {
    private String id;
    private String name;
    private int score;
    private char isAssistant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public char getIsAssistant() {
        return isAssistant;
    }

    public void setIsAssistant(char isAssistant) {
        this.isAssistant = isAssistant;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", isAssistant=" + isAssistant +
                '}';
    }
}
