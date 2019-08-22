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

package com.snsoft.teamreading.interceptor;

import java.io.Serializable;

class InterceptorReturnData implements Serializable {
    private int code;
    private String msg;

    int getCode() {
        return code;
    }

    void setCode(int code) {
        this.code = code;
    }

    String getMsg() {
        return msg;
    }

    void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "InterceptorReturnData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
