/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.07
 *
 * @Description 登录拦截器，从 session中检查登录信息
 */

package com.snsoft.readingsystem.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        if (request.getSession().getAttribute("user") == null) {
            request.getRequestDispatcher("/login.html").forward(request, response);
            return false;
        } else
            return true;
    }

}
