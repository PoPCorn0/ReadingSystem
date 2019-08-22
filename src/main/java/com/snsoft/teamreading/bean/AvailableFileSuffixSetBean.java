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

package com.snsoft.teamreading.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class AvailableFileSuffixSetBean {
    @Bean
    public Set<String> getAvailableFileSuffixSet() {
        HashSet<String> set = new HashSet<>();
        set.add(".zip");
        set.add(".rar");
        set.add(".7z");
        set.add(".jpg");
        set.add(".png");
        set.add(".gif");
        set.add(".raw");
        set.add(".jpeg");
        set.add(".bmp");
        set.add(".avi");
        set.add(".rmvb");
        set.add(".mp4");
        set.add(".wma");
        set.add(".wmv");
        set.add(".mkv");
        set.add(".flv");
        set.add(".doc");
        set.add(".docx");
        set.add(".xsl");
        set.add(".xsls");
        set.add(".ppt");
        set.add(".pptx");
        set.add(".xlsm");
        return set;
    }
}
