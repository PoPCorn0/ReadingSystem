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

public class Student implements Serializable {
    private String id;
    private String pwd;
    private String name;
    private int score;
    private char isRemoved;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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

    public char getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(char isRemoved) {
        this.isRemoved = isRemoved;
    }

    @Override
    public String toString() {
        return JSON.toJSONString((this));
    }
}
