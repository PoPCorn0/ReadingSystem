/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.08
 *
 * @Description 身份拦截器，只有学生身份放行
 */

package com.snsoft.readingsystem.interceptor;

import com.alibaba.fastjson.JSON;
import com.snsoft.readingsystem.utils.AllConstant;
import com.snsoft.readingsystem.utils.InterceptorReturnData;
import com.snsoft.readingsystem.utils.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user.getIdentityMark() != AllConstant.IDENTITYMARK_STUDENT) {
            InterceptorReturnData data = new InterceptorReturnData();
            data.setCode(AllConstant.CODE_FAILED);
            data.setMsg(AllConstant.MSG_PERMISSION_DENIED);
            response.getWriter().print(JSON.toJSONString(data));
            return false;
        } else {
            return true;
        }
    }
}
