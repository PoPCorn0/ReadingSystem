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

public class TeamStu implements Serializable {
    private String teamId;
    private String studentId;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "TeamStu{" +
                ", teamId='" + teamId + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}
