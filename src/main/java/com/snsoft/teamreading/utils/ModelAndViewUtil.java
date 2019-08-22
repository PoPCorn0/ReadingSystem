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

import com.snsoft.teamreading.enums.Code;
import com.snsoft.teamreading.enums.Msg;
import org.springframework.web.servlet.ModelAndView;

/**
 * 为ModelAndView添加数据的工具类
 */
public class ModelAndViewUtil {

    public static ModelAndView addObject(ModelAndView mv, Code code) {
        if (code == Code.SUCCESS) {
            mv.addObject("code", Code.SUCCESS.getCode());
            mv.addObject("msg", Msg.SUCCESS.getMsg());
        } else if (code == Code.FAIL) {
            mv.addObject("code", Code.FAIL.getCode());
            mv.addObject("msg", Msg.FAIL.getMsg());
        } else if (code == Code.ERROR) {
            mv.addObject("code", Code.ERROR.getCode());
            mv.addObject("msg", Msg.ERROR.getMsg());
        }
        return mv;
    }

    public static ModelAndView addObject(ModelAndView mv, String dataName, Object data) {
        mv.addObject("code", Code.SUCCESS.getCode());
        mv.addObject("msg", Msg.SUCCESS.getMsg());
        mv.addObject(dataName, data);
        return mv;
    }

    public static ModelAndView addObject(ModelAndView mv, Code code, String msg) {
        if (code == Code.SUCCESS) {
            mv.addObject("code", Code.SUCCESS.getCode());
            mv.addObject("msg", msg);
        } else if (code == Code.FAIL) {
            mv.addObject("code", Code.FAIL.getCode());
            mv.addObject("msg", msg);
        } else if (code == Code.ERROR) {
            mv.addObject("code", Code.ERROR.getCode());
            mv.addObject("msg", msg);
        }
        return mv;
    }

}
