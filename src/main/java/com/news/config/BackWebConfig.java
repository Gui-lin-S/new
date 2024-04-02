package com.news.config;

import com.news.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Configuration
public class BackWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/back/**")
                .excludePathPatterns("/back/login","/back/user/loginResult","/css/**","/fonts/**","/images/**","/js/**",
                        "/xadmin/**","/layer/**","/upload/**");
    }
}
