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

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String pwd;
    private char identityMark;

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

    public char getIdentityMark() {
        return identityMark;
    }

    public void setIdentityMark(char identityMark) {
        this.identityMark = identityMark;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                ", identityMark=" + identityMark +
                '}';
    }
}
