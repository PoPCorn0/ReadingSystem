/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.15
 *
 * @Description 用于判断上传的文件后缀名是否合法
 */

package com.snsoft.readingsystem.utils;

import java.util.HashSet;
import java.util.Set;

public class AvailableFileSuffixUtil {

    private static final Set<String> suffix = new HashSet<>();

    static {
        suffix.add(".zip");
        suffix.add(".rar");
        suffix.add(".7z");
        suffix.add(".jpg");
        suffix.add(".png");
        suffix.add(".gif");
        suffix.add(".raw");
        suffix.add(".jpeg");
        suffix.add(".bmp");
        suffix.add(".avi");
        suffix.add(".rmvb");
        suffix.add(".mp4");
        suffix.add(".wma");
        suffix.add(".wmv");
        suffix.add(".mkv");
        suffix.add(".flv");
        suffix.add(".doc");
        suffix.add(".docx");
        suffix.add(".xls");
        suffix.add(".xlsx");
        suffix.add(".ppt");
        suffix.add(".pptx");
    }

    public static boolean contains(String s) {
        return suffix.contains(s);
    }
}
