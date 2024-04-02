package com.news.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.news.domain.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Mapper
public interface CateMapper extends BaseMapper<Category> {
}
