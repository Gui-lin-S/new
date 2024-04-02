package com.news.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.news.domain.Article;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author 归林
 * @date 2024/4/2
 */
public interface ArticleService extends IService<Article> {
    /**
     * 新闻保存，添加了路径信息
     * @param article
     * @param session
     * @return
     */
    Map savaArticle(Article article, HttpSession session);

    /**
     * 查询所有新闻，添加了分类名字
     * @return
     */
    List<Article> addCateAll();

    /**
     * 首页查询所有数据，分页处理，添加了category实体类
     * @param current
     * @param size
     * @param session
     * @return
     * @throws Exception
     */
    IPage<Article> findByPage(Integer current, Integer size, HttpSession session) throws Exception;

    /**
     * 查询文章详情，添加了category实体类和作者信息
     * @param aid
     * @return
     */
    Article findMessageId(Integer aid);

    /**
     * 查找所有未审核文章，并能够根据搜索条件查找，其中文章等级小于用户本身等级
     * @param page
     * @param uname
     * @param cname
     * @param title
     * @param session
     * @param status
     * @return
     */
    IPage<Article> findAllByStatus(IPage<Article> page,String uname,String cname,String title,HttpSession session,Integer status);

    /**
     * 新闻收藏
     * @param id
     * @param session
     * @return
     */
    Map artFavorite(Integer id, HttpSession session);

    /**
     * 获取排行榜
     * @param session
     */
    void getRowData(HttpSession session);

    /**
     * 获取最新评论的新闻排行榜
     * @param session
     * @return
     */
    List<Article> getNewComArt(HttpSession session);

    /**
     * 进行新闻搜素
     * @param session
     * @param keyword
     * @return
     */
    List<Article> searchArt(HttpSession session,String keyword);

    /**
     * 查询其他等级新闻数据，分页处理，添加了category实体类
     * @param pageNum
     * @param pageSize
     * @param cid
     * @param level
     * @param keyword
     * @return
     * @throws Exception
     */
    IPage<Article> findOtherLevPage(Integer pageNum, Integer pageSize,Integer cid,Integer level,
                                    String keyword) throws Exception;
}
