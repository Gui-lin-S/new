package com.news.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.news.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 动态sql，查找未审核文章的多表查询
     * @param page
     * @param lid
     * @param illegal
     * @param uname
     * @param userLid
     * @return
     */
    IPage<Comment> findComByStatus(IPage<Comment> page, @Param("lid") Integer lid,
                                   @Param("illegal") Integer illegal, @Param("uname") String uname,
                                   @Param("userLid")Integer userLid);
}
