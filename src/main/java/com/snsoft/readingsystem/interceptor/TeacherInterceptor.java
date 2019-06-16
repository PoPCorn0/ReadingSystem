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

package com.snsoft.readingsystem.interceptor;

import com.alibaba.fastjson.JSON;
import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.enums.Identity;
import com.snsoft.readingsystem.enums.Msg;
import com.snsoft.readingsystem.utils.InterceptorReturnData;
import com.snsoft.readingsystem.pojo.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TeacherInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        User user = (User) request.getSession().getAttribute("user");
        if (user.getIdentityMark() != Identity.TEACHER.getIdentity()) {
            InterceptorReturnData data = new InterceptorReturnData();
            data.setCode(Code.FAIL.getCode());
            data.setMsg(Msg.PERMISSION_DENIED.getMsg());
            response.getWriter().print(JSON.toJSONString(data));
            return false;
        } else
            return true;
    }
}
