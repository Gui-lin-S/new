package com.news.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.news.domain.Article;
import com.news.domain.Category;
import com.news.domain.Comment;
import com.news.domain.User;
import com.news.service.ArticleService;
import com.news.service.CateService;
import com.news.service.CommentService;
import com.news.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Slf4j
@Controller
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @Autowired
    CateService cateService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("article_pub")
    public String article_Pub(@RequestParam(value = "id",required = false)Integer id, Model model) {
        Article byId = articleService.getById(id);
        model.addAttribute("byId",byId);

        List<Category> categories = cateService.list();
        model.addAttribute("categories",categories);
        return "article_pub";
    }

    /**
     * 上传图片等资源文件
     * @param request
     * @param file
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/upload/uploadFile")
    public Map uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        if (file != null) {
            //存放到项目静态资源下
            String webapp = "src/main/resources/";
            try {
                //图片名字
                String substring = file.getOriginalFilename();
                //使用uuid替代原来名字
                String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                //使用uuid上传将上传的图片重命名，但是遇到改名之后上传较慢，需要等待传输才能回显
                String uuidName = uuid + "." + substring.substring(substring.lastIndexOf(".") + 1);
                String fileName = "/static/upload/" + uuidName;
                // 图片的在服务器上面的物理路径
                File destFile = new File(webapp, fileName);
                // 生成upload目录
                File parentFile = destFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();// 自动生成upload目录
                }
                // 把上传的临时图片，复制到当前项目的webapp路径
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(destFile));
                map = new HashMap<>();
                map2 = new HashMap<>();
                map.put("code", 0);
                map.put("msg", "上传成功");
                map.put("data", map2);
                map2.put("src", "/upload/"+uuidName);
                map2.put("title", uuidName);
                log.info("图片地址为{}",uuidName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 文章保存
     * @param article
     * @param session
     * @return
     */
    @PostMapping("/articleSave")
    @ResponseBody
    public Map articleSave(@RequestBody Article article, HttpSession session) {
        Map map1 = articleService.savaArticle(article, session);
        return map1;
    }

    /**
     * 通过列表点击文章跳转
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/article/details/{id}")
    public String findArticalDetail(@PathVariable("id")Integer id,Model model){
        //获取点击新闻的内容
        Article articleById = articleService.findMessageId(id);
        //获取该新闻下评论
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        List<Comment> commentList = commentService.list(wrapper.eq("aid", id).orderByAsc("com_time"));
        //获取相关评论的用户信息
        List<User> comUser = commentService.getComUser(id);

        model.addAttribute("articleById",articleById);
        model.addAttribute("commentList",commentList);
        model.addAttribute("comUser",comUser);
        return "article_details";
    }

    /**
     * 访问指定新闻分类
     * @param cid
     * @param model
     * @return
     */
    @GetMapping("/cate/CateArticle/{cid}")
    public String CateArticle(@PathVariable("cid")Integer cid, Model model){

        Category cateById = cateService.getById(cid);
        model.addAttribute("cateById",cateById);

        return "article";
    }

    /**
     * 新闻分类查询结果返回分页结果
     * @param pageNum
     * @param pageSize
     * @param cateId
     * @param session
     * @return
     */
    @RequestMapping("/cate/queryCaetAll")
    @ResponseBody
    public Map queryCateAll(Integer pageNum,Integer pageSize,Integer cateId,HttpSession session) {

        //使用MP自带的分页插件分页查询数据
        Page<Article> catePage = new Page<>(pageNum, pageSize);
        //分页查询结果
        QueryWrapper<Article> cateQuery = new QueryWrapper<>();
        //查询条件,用户有登录显示为用户等级的查询，没有登录查询的是最低的等级
        User user = (User) session.getAttribute("user");
        if (user!=null){
            cateQuery.eq("cid",cateId).eq("lid",user.getLid())
                    .eq("status",1).orderByDesc("create_time");
        }else {
            cateQuery.eq("cid",cateId).eq("lid",1)
                    .eq("status",1).orderByDesc("create_time");
        }

        Page<Article> catepages = articleService.page(catePage, cateQuery);
        List<Article> cateArtList = catepages.getRecords();

        long total = catepages.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("data",cateArtList);
        map.put("count",total);
        map.put("status",200);
        return map;
    }

    /**
     * 进行新闻收藏
     * @param id
     * @param session
     * @return
     */
    @PostMapping("/article/fav")
    @ResponseBody
    public Map artFavorite(Integer id,HttpSession session){
        Map map = articleService.artFavorite(id, session);
        return map;
    }

    /**
     * 新闻搜索
     * @param keyword
     * @param model
     * @param session
     * @return
     */
    @PostMapping("/article/search")
    public String search(String keyword,Model model,HttpSession session){
        //返回搜素的内容
        model.addAttribute("keyword",keyword);
        //返回搜素结果
        List<Article> articles = articleService.searchArt(session, keyword);
        model.addAttribute("articles",articles);
        return "search";
    }

    /**
     * 展示其他等级新闻
     * @param lid
     * @param model
     * @param level
     * @param cid
     * @param keyword
     * @return
     */
    @RequestMapping("/article/other/{lid}")
    public  String otherArticle(@PathVariable("lid")Integer lid,Model model,
                                @RequestParam(value = "level",required = false)Integer level,
                                @RequestParam(value = "cid",required = false)Integer cid,
                                @RequestParam(value = "keyword",required = false)String keyword){
        model.addAttribute("level",lid);
        model.addAttribute("cid",cid);
        model.addAttribute("keyword",keyword);
        return "other_level";
    }

    /**
     * 返回其他等级查询结果，可分页
     * @param pageNum
     * @param pageSize
     * @param cid
     * @param level
     * @param keyword
     * @return
     */
    @RequestMapping("/article/other/levelResult")
    @ResponseBody
    public Map queryOtherLevResult(Integer pageNum,Integer pageSize,Integer cid,Integer level,
                                   String keyword) {
        Map<String, Object> map = new HashMap<>();
        try {
            IPage<Article> page = articleService.findOtherLevPage(pageNum, pageSize,cid,level,keyword);
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
