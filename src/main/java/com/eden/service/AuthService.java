package com.eden.service;

import com.eden.entity.SysUser;

public interface AuthService {
    //登录
    SysUser login(String username, String password);
    //注册
    boolean register(String username, String password);
}
