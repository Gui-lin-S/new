package com.news.controller.back_system;

import com.news.service.CateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Slf4j
@Controller
@RequestMapping("/back")
public class BackController {
    @Autowired
    CateService cateService;

    /**
     * 后台系统登录页
     * @return
     */
    @RequestMapping("/login")
    public String backLogin(){
        return "back/login";
    }

    /**
     * 后台系统首页
     * @return
     */
    @RequestMapping("/index")
    public String backIndex(){
        return "back/index";
    }

    /**
     * 欢迎页
     * @return
     */
    @RequestMapping("/welcome")
    public String backWelcome(){
        return "back/welcome";
    }
}
