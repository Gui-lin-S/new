package com.news.controller;

import com.news.domain.Comment;
import com.news.service.CommentService;
import com.news.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Slf4j
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    UserService userServiced;

    /**
     * 用户添加评论
     * @param comment
     * @param session
     * @return
     */
    @PostMapping("/addCom")
    @ResponseBody
    public Map addComment(@RequestBody Comment comment, HttpSession session){
        log.info("{}",comment);
        System.out.println(comment);
        System.out.println(session);
        Map map = commentService.addComment(comment, session);
        return map;

    }
}
