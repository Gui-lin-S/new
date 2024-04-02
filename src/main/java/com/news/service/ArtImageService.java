package com.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.news.domain.ArtImage;
import com.news.domain.Article;

/**
 * @author 归林
 * @date 2024/4/2
 */
public interface ArtImageService extends IService<ArtImage> {
    /**
     * 保存新闻图片
     * @param article
     */
    void saveArtImage(Article article);
}
