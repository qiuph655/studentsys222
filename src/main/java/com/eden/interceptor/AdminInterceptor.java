package com.eden.interceptor;

import com.eden.entity.SysUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        SysUser sysUser = (SysUser) request.getSession().getAttribute("loginUser");
        if (sysUser == null) {
            response.setStatus(401);
            return false;
        }
//        if(sysUser.getRole().equals("admin")){}
        if(!"ADMIN".equals(sysUser.getRole())) {
            response.setStatus(403);
            return false;
        }
        return true;
    }
}
