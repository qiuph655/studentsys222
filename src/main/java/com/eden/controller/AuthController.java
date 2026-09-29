package com.eden.controller;

import com.eden.entity.SysUser;
import com.eden.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService=authService;
    }
    @PostMapping("/login")
    public Map<String,Object> login(@RequestParam String username,
                                    @RequestParam String password,
                                    HttpSession session){
        Map<String,Object> res=new HashMap<>();
        SysUser user=authService.login(username,password);
        if(user==null){
            res.put("success",false);
            res.put("msg","用户名密码错误");
            return res;
        }
        session.setAttribute("loginUser",user);
        res.put("success",true);
        res.put("role",user.getRole());
        res.put("msg","登录成功");
        return res;
    }
    @PostMapping("/register")
    public Map<String,Object> register(@RequestParam String username,
                                       @RequestParam String password){
        Map<String,Object> res=new HashMap<>();
        if(username==null||username.trim().isEmpty()){
            res.put("success",false);
            res.put("msg","用户名不能为空");
            return res;
        }
        if(password==null||password.trim().isEmpty()){
            res.put("success",false);
            res.put("msg","密码不能为空");
            return res;
        }
        boolean ok=authService.register(username,password);
        if(!ok){
            res.put("success",false);
            res.put("msg","注册失败，用户名可能存在");
            return res;
        }
        res.put("success",true);
        res.put("msg","注册成功，请登录");
        return res;
    }
    @PostMapping("/logout")
    public Map<String,Object>logout(HttpSession session){
        session.invalidate();
        Map<String,Object> res=new HashMap<>();
        res.put("success",true);
        res.put("msg","已退出登录");
        return res;
    }
}
