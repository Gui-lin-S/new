package com.news.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news.domain.ArtImage;
import com.news.domain.Article;
import com.news.mapper.ArtImageMapper;
import com.news.mapper.ArticleMapper;
import com.news.service.ArtImageService;
import com.news.utils.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Slf4j
@Service
public class ArtImageServiceImpl extends ServiceImpl<ArtImageMapper, ArtImage> implements ArtImageService {
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArtImageMapper imageMapper;
    @Override
    public void saveArtImage(Article article) {
        String content = article.getContent();
        List<String> imgStr = ImageUtil.getImgStr(content);

        ArtImage artImage = new ArtImage();
        if (imgStr==null||imgStr.size()==0){
            artImage.setArtId(article.getId());
            artImage.setImageSrc("/images/white.jpg");
        }else {
            //取第一张图片即可
            String src = imgStr.get(0);
            artImage.setArtId(article.getId());
            artImage.setImageSrc(src);
        }
        imageMapper.insert(artImage);
    }
}
