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

package com.snsoft.teamreading.enums;

public enum Identity {
    TEACHER('0'),
    STUDENT('1');

    private final char identity;

    Identity(char identity) {
        this.identity = identity;
    }

    public char getIdentity() {
        return identity;
    }
}
