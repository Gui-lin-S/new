package com.news.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.news.domain.Comment;
import com.news.domain.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author 归林
 * @date 2024/4/2
 */
public interface CommentService extends IService<Comment> {
    /**
     * 添加评论
     * @param comment
     * @param session
     * @return
     */
    Map addComment(Comment comment, HttpSession session);

    /**
     * 获取评论用户
     * @param aid
     * @return
     */
    List<User> getComUser(Integer aid);

    /**
     * 后台评论管理，可根据条件查询
     * @param page
     * @param lid
     * @param illegal
     * @param uname
     * @param userLid
     * @return
     */
    IPage<Comment> findComByStatus(IPage<Comment> page, Integer lid, Integer illegal, String uname, Integer userLid);
}
