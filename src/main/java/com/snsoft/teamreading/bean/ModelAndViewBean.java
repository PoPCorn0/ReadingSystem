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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class ModelAndViewBean {

    @Bean
    @Scope("prototype")
    public ModelAndView getModelAndView(@Autowired MappingJackson2JsonView mappingJackson2JsonView) {
        ModelAndView mv = new ModelAndView();
        mv.setView(mappingJackson2JsonView);
        return mv;
    }

}
