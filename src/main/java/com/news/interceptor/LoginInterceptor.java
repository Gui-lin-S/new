package com.news.interceptor;

import com.news.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("拦截的请求路径为{}",requestURI);
        User user = (User)request.getSession().getAttribute("back_user");
        if (user != null) {
            //是否激活
            return user.getLid() >= 2;
        }else {
            request.setAttribute("msg", "请先激活");
            request.getRequestDispatcher("/back/login").forward(request, response);
            return false;
        }
    }
}
