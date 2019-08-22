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

package com.snsoft.teamreading.utils;

import org.apache.ibatis.session.RowBounds;

public class RowBoundsUtil {
    public static RowBounds getRowBounds(Integer page) {
        if (page == null) {
            return new RowBounds(0, Constant.PAGE_AMOUNT);
        } else
            return new RowBounds(Constant.PAGE_AMOUNT * (page - 1), Constant.PAGE_AMOUNT);
    }
}
