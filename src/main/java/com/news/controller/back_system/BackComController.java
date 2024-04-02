package com.news.controller.back_system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.news.domain.Comment;
import com.news.domain.User;
import com.news.service.CommentService;
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
@RequestMapping("/back/comment")
public class BackComController {
    @Autowired
    CommentService commentService;

    /**
     * 新闻评论展示列表
     * @param pn
     * @param uname
     * @param lid
     * @param illegal
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/comManage")
    public String commentManage(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
                                @RequestParam(value = "uname",required = false)String uname,
                                @RequestParam(value = "lid",required = false)Integer lid,
                                @RequestParam( value = "illegal",required = false)Integer illegal,
                                Model model, HttpSession session){
        User back_user = (User) session.getAttribute("back_user");
        System.out.println(uname+":"+lid+":"+illegal);
        //默认第一页开始，一页20条
        Page<Comment> page = new Page<>(pn, 20);
        IPage<Comment> comPage = commentService.findComByStatus(page, lid, illegal, uname, back_user.getLid());
        //返回总页数
        long comTotalPage = comPage.getPages();

        model.addAttribute("comPage",comPage);
        model.addAttribute("comTotalPage",comTotalPage);
        return "back/comment-manage";
    }

    /**
     * 当评论过长，可查看评论详细页面
     * @param comid
     * @param model
     * @return
     */
    @RequestMapping("/view")
    public String viewComment(Integer comid,Model model){
        Comment comment = commentService.getById(comid);
        model.addAttribute("comment",comment);
        return "back/comment-view";
    }

    /**
     * 新闻评论页面进行评论删除操作
     * @param comid
     * @return
     */
    @PostMapping("/deleteComment")
    @ResponseBody
    public String deleteComment(Integer comid){
        commentService.removeById(comid);
        log.info("评论表主键comid：{}",comid);
        return "success";
    }
}
