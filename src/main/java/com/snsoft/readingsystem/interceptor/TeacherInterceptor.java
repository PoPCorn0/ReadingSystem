/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.07
 *
 * @Description 身份拦截器，至有导师身份放行
 */

package com.snsoft.readingsystem.interceptor;

import com.alibaba.fastjson.JSON;
import com.snsoft.readingsystem.utils.AllConstant;
import com.snsoft.readingsystem.utils.InterceptorReturnData;
import com.snsoft.readingsystem.utils.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TeacherInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        User user = (User) request.getSession().getAttribute("user");
        if (user.getIdentityMark() != AllConstant.IDENTITYMARK_TEACHER) {
            InterceptorReturnData data = new InterceptorReturnData();
            data.setCode(AllConstant.CODE_FAILED);
            data.setMsg(AllConstant.MSG_PERMISSION_DENIED);
            response.getWriter().print(JSON.toJSONString(data));
            return false;
        } else
            return true;
    }
}
