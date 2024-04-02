package com.news.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.news.domain.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 添加
     * @return
     */
    List<Article> addCateAll();

    /**
     * 分页
     * @param page
     * @param queryWrapper
     * @return
     * @throws Exception
     */
    IPage<Article> findByPage(IPage<Article> page, @Param("ew") QueryWrapper<Article> queryWrapper) throws Exception;

    /**
     * 通过id查找文章详细信息，多表查询
     * @param aid
     * @return
     */
    Article findMessageId(Integer aid);

    /**
     * 动态sql，查找未审核文章的多表查询
     * @param page
     * @param uname
     * @param cname
     * @param title
     * @param lid
     * @param status
     * @return
     */
    IPage<Article> findAllByStatus(IPage<Article> page, @Param("uname") String uname, @Param("cname") String cname,
                                   @Param("title") String title, @Param("lid") Integer lid, @Param("status") Integer status);
}
