package com.eden.config;

import com.eden.interceptor.AdminInterceptor;
import com.eden.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/login","/api/register","/uploads/**");
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/api/student/add","/api/student/update","/api/student/delete","/api/student/batchDelete","/api/student/get","/api/student/upload");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(Paths.get(System.getProperty("user.dir"), "uploads").toUri().toString() + "/");
    }
}
