package com.news.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news.domain.Category;
import com.news.mapper.CateMapper;
import com.news.service.CateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Slf4j
@Service
public class CateServiceImpl extends ServiceImpl<CateMapper, Category> implements CateService {
}
