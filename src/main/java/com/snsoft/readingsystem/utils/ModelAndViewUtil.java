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

package com.snsoft.readingsystem.utils;

import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.enums.Msg;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class ModelAndViewUtil {
    public static ModelAndView getModelAndView(Code code) {
        ModelAndView mv = new ModelAndView();
        mv.setView(new MappingJackson2JsonView());
        if (code == Code.SUCCESS) {
            mv.addObject("code", Code.SUCCESS);
            mv.addObject("msg", Msg.SUCCESS.getMsg());
        } else if (code == Code.FAIL) {
            mv.addObject("code", Code.FAIL);
            mv.addObject("msg", Msg.FAIL.getMsg());
        } else if (code == Code.ERROR) {
            mv.addObject("code", Code.ERROR);
            mv.addObject("msg", Msg.ERROR.getMsg());
        }
        return mv;
    }

    public static ModelAndView getModelAndView(String dataName, Object data) {
        ModelAndView mv = new ModelAndView();
        mv.setView(new MappingJackson2JsonView());
        mv.addObject("code", Code.SUCCESS);
        mv.addObject("msg", Msg.SUCCESS.getMsg());
        mv.addObject(dataName, data);
        return mv;
    }

    public static ModelAndView getModelAndView(Code code, String msg) {
        ModelAndView mv = new ModelAndView();
        mv.setView(new MappingJackson2JsonView());
        if (code == Code.SUCCESS) {
            mv.addObject("code", Code.SUCCESS);
            mv.addObject("msg", msg);
        } else if (code == Code.FAIL) {
            mv.addObject("code", Code.FAIL);
            mv.addObject("msg", msg);
        } else if (code == Code.ERROR) {
            mv.addObject("code", Code.ERROR);
            mv.addObject("msg", msg);
        }
        return mv;
    }

}
