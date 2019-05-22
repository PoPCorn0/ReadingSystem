/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.22
 *
 * @Description
 */

package com.snsoft.readingsystem.pojo;

import java.util.Set;

public class AvailableFileSuffix {
    private Set<String> suffix;

    public Set<String> getSuffix() {
        return suffix;
    }

    public void setSuffix(Set<String> suffix) {
        this.suffix = suffix;
    }

    public boolean contains(String s) {
        return suffix.contains(s);
    }

}
