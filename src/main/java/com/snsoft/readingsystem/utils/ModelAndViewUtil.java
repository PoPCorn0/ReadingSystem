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

package com.snsoft.readingsystem.utils;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class ModelAndViewUtil {
    public static ModelAndView getModelAndView(int code) {
        ModelAndView mv = new ModelAndView();
        mv.setView(new MappingJackson2JsonView());
        if (code == AllConstant.CODE_SUCCESS) {
            mv.addObject("code", AllConstant.CODE_SUCCESS);
            mv.addObject("msg", AllConstant.MSG_SUCCESS);
        } else if (code == AllConstant.CODE_FAILED) {
            mv.addObject("code", AllConstant.CODE_FAILED);
            mv.addObject("msg", AllConstant.MSG_FAILED);
        } else if (code == AllConstant.CODE_ERROR) {
            mv.addObject("code", AllConstant.CODE_ERROR);
            mv.addObject("msg", AllConstant.MSG_ERROR);
        }
        return mv;
    }

    public static ModelAndView getModelAndView(String dataName, Object data) {
        ModelAndView mv = new ModelAndView();
        mv.setView(new MappingJackson2JsonView());
        mv.addObject("code", AllConstant.CODE_SUCCESS);
        mv.addObject("msg", AllConstant.MSG_SUCCESS);
        mv.addObject(dataName, data);
        return mv;
    }

    public static ModelAndView getModelAndView(int code, String msg) {
        ModelAndView mv = new ModelAndView();
        mv.setView(new MappingJackson2JsonView());
        if (code == AllConstant.CODE_SUCCESS) {
            mv.addObject("code", AllConstant.CODE_SUCCESS);
            mv.addObject("msg", msg);
        } else if (code == AllConstant.CODE_FAILED) {
            mv.addObject("code", AllConstant.CODE_FAILED);
            mv.addObject("msg", msg);
        } else if (code == AllConstant.CODE_ERROR) {
            mv.addObject("code", AllConstant.CODE_ERROR);
            mv.addObject("msg", msg);
        }
        return mv;
    }

    public static ModelAndView getLoginModelAndView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }
}
