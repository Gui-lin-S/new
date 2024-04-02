package com.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.news.domain.Article;
import com.news.domain.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author 归林
 * @date 2024/4/2
 */
public interface UserService extends IService<User> {
    /**
     * 注册
     * @param map
     * @param session
     * @return
     */
    Map registerResult(Map<String,String> map, HttpSession session);

    /**
     * 登录
     * @param map
     * @param session
     * @return
     */
    Map loginResult(Map<String,String> map, HttpSession session);

    /**
     * 返回登录
     * @param map
     * @param session
     * @return
     */
    Map backLoginResult(Map<String, String> map, HttpSession session);

    /**
     * 获取收藏
     * @param uid
     * @return
     */
    List<Article> getFavArt(Integer uid);

    /**
     * 图片路径
     * @param map
     * @param session
     * @return
     */
    Map alterPass(Map<String,String> map,HttpSession session);
}
