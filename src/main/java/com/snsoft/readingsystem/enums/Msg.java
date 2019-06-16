/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.06.16
 *
 * @Description
 */

package com.snsoft.readingsystem.enums;

public enum Msg {
    SUCCESS("操作成功"),
    FAIL("操作失败"),
    ERROR("发生异常"),
    PERMISSION_DENIED("权限不足");

    private final String msg;

    Msg(String s) {
        this.msg = s;
    }

    public String getMsg() {
        return msg;
    }
}
