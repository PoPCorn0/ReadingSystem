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

public class StuTask implements Serializable {
    private String taskId;
    private String studentId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "StuTask{" +
                "taskId='" + taskId + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}