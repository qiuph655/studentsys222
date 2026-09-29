package com.eden.interceptor;

import com.eden.entity.SysUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SysUser user = (SysUser) request.getSession().getAttribute("loginUser");
        if (user == null) {
//            response.sendRedirect("/login");
            response.setStatus(401);
            return false;
        }
        return true;

    }
}
