package com.news.controller.back_system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.news.domain.Article;
import com.news.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Slf4j
@Controller
@RequestMapping("/back/article")
public class BackArticleController {
    @Autowired
    ArticleService articleService;

    /**
     * 新闻审核
     * @param pn
     * @param uname
     * @param cname
     * @param title
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/review")
    public String reviewPage(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
                             @RequestParam(value = "uname",required = false)String uname,
                             @RequestParam(value = "cname",required = false)String cname,
                             @RequestParam( value = "title",required = false)String title,
                             Model model, HttpSession session){
        IPage<Article> page = new Page<>(pn, 20);
        //多表查询，status为未审核文章
        IPage<Article> pageStatus = articleService.findAllByStatus(page, uname, cname, title, session,0);
        model.addAttribute("pageStatus",pageStatus);
        //返回总页数
        long pageTotal = pageStatus.getPages();

        model.addAttribute("pageTotal",pageTotal);
        return "back/article-review";
    }

    /**
     * 后台审核查看文章详情页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/view")
    public String viewArticle(Integer id,Model model){
        Article article = articleService.findMessageId(id);
        model.addAttribute("article",article);
        return "back/article-view";

    }

    /**
     * 后台审核文章通过
     * @param id
     * @return
     */
    @RequestMapping("/permit")
    @ResponseBody
    public String articlePermit(Integer id){
        Article article = articleService.getById(id);
        article.setStatus(1);
        boolean flag = articleService.updateById(article);
        return ""+flag;
    }

    /**
     * 后台审核文章否决
     * @param id
     * @return
     */
    @RequestMapping("/deny")
    @ResponseBody
    public String articleDeny(Integer id){
        Article article = articleService.getById(id);
        article.setStatus(2);
        boolean flag = articleService.updateById(article);
        return ""+flag;
    }

    /**
     * 新闻审核不通过页面
     * @param pn
     * @param uname
     * @param cname
     * @param title
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/denyPage")
    public String dengPage(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
                           @RequestParam(value = "uname",required = false)String uname,
                           @RequestParam(value = "cname",required = false)String cname,
                           @RequestParam( value = "title",required = false)String title,
                           Model model, HttpSession session){

        //默认第一页开始，一页20条
        IPage<Article> page = new Page<>(pn, 20);
        //多表查询，status为未审核文章
        IPage<Article> denyStatus = articleService.findAllByStatus(page, uname, cname, title, session,2);
        model.addAttribute("denyStatus",denyStatus);
        long denyTotal = denyStatus.getPages();

        model.addAttribute("denyTotal",denyTotal);

        return "back/article-deny";
    }

    /**
     * 新闻审核通过页面
     * @param pn
     * @param uname
     * @param cname
     * @param title
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/permitPage")
    public String permitPage(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
                             @RequestParam(value = "uname",required = false)String uname,
                             @RequestParam(value = "cname",required = false)String cname,
                             @RequestParam( value = "title",required = false)String title,
                             Model model, HttpSession session){

        //默认第一页开始，一页20条
        IPage<Article> page = new Page<>(pn, 20);
        //多表查询，status为未审核文章
        IPage<Article> permitStatus = articleService.findAllByStatus(page, uname, cname, title, session,1);
        model.addAttribute("permitStatus",permitStatus);
        //返回总页数
        long permitTotal = permitStatus.getPages();

        model.addAttribute("permitTotal",permitTotal);

        return "back/article-permit";
    }

    /**
     * 新闻发布页面进行文章删除操作
     * @param id
     * @return
     */
    @PostMapping("/deleteArticle")
    @ResponseBody
    public String deleteArticle(Integer id){
        articleService.removeById(id);
        return "success";
    }
}
