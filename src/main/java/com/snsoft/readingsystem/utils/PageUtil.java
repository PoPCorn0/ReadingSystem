/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.13
 *
 * @Description
 */

package com.snsoft.readingsystem.utils;

import org.apache.ibatis.session.RowBounds;

public class PageUtil {
    public static RowBounds getRowBounds(Integer page) {
        if (page == null) {
            return new RowBounds(0, AllConstant.PAGE_AMOUNT);
        } else
            return new RowBounds(AllConstant.PAGE_AMOUNT * (page - 1), AllConstant.PAGE_AMOUNT);
    }
}
