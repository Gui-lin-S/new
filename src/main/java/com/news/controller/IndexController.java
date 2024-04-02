package com.news.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.news.domain.Article;
import com.news.domain.Category;
import com.news.service.ArtImageService;
import com.news.service.ArticleService;
import com.news.service.CateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Slf4j
@Controller
public class IndexController {
    @Autowired
    ArticleService articleService;

    @Autowired
    ArtImageService imageService;

    @Autowired
    CateService cateService;

    /**
     * 时间线页面
     * @param model
     * @return
     */
    @RequestMapping("/time_line")
    public String timeLine(Model model) {
        return "time_line";
    }

    @RequestMapping("/tips")
    public String tips(Model model) {
        return "tips";
    }

    /**
     * 网站首页
     * @param pn
     * @param model
     * @param session
     * @return
     */
    @RequestMapping( value = {"/index","/"})
    public String index(@RequestParam(value = "pn",defaultValue = "1")Integer pn, Model model, HttpSession session) {
        //查询所有文章信息
        List<Article> articleList = articleService.list();
        //使用MP自带的分页插件分页查询数据
        Page<Article> articlePage = new Page<>(pn, 8);
        //分页查询结果
        Page<Article> pages = articleService.page(articlePage, null);
        //所有记录放在record里面
        model.addAttribute("pages",pages);
        model.addAttribute("currPn",pn);
        //将分类的cid保存到session中
        List<Category> list = cateService.list();
        session.setAttribute("cateList",list);
        //调用排行榜方法
        articleService.getRowData(session);
        //调用最新评论新闻方法
        List<Article> newComArt = articleService.getNewComArt(session);
        session.setAttribute("newComArt",newComArt);
        //返回标签列表
        return "index";
    }

    /**
     * 首页列表请求数据
     * @param pageNum
     * @param pageSize
     * @param session
     * @return
     */
    @RequestMapping("/index/queryAll")
    @ResponseBody
    public Map queryAll(Integer pageNum, Integer pageSize, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        try {
            IPage<Article> page = articleService.findByPage(pageNum, pageSize,session);
            List<Article> addCateList = page.getRecords();
            long total = page.getTotal();
            map.put("data",addCateList);
            map.put("count",total);
            map.put("status",200);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return map;
    }
}
