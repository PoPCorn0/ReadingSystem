/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.08
 *
 * @Description 身份拦截器，审核者必须和被审核者同时在一个团队中并且审核者为该团队的导师助手，否则没有权限
 */

package com.snsoft.readingsystem.interceptor;

import com.snsoft.readingsystem.utils.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        String uri = request.getRequestURI();

        //TODO

        // 这个id可能为待审核解读&追加解读id，也可能为待审核任务id
        String id = (String) request.getAttribute("id");


        return true;
    }
}
