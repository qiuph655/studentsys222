package com.eden.service.impl;

import com.eden.entity.SysUser;
import com.eden.repository.SysUserRepository;
import com.eden.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final SysUserRepository sysUserRepository;

    public AuthServiceImpl(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    public SysUser login(String username, String password) {
        SysUser user = sysUserRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        if (!user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }

    @Override
    public boolean register(String username, String password) {
        SysUser oldUser = sysUserRepository.findByUsername(username);
        if (oldUser != null) {
            return false;
        }
        SysUser newUser = new SysUser();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole("USER");
        sysUserRepository.save(newUser);
        return true;
    }
}
