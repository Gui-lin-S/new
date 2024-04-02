package com.news.controller;

import com.news.domain.Category;
import com.news.service.CateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Slf4j
@Controller
@RequestMapping("/common")
public class CommonController {
    @Autowired
    CateService cateService;

    @RequestMapping("/getCategory")
    @ResponseBody
    public List<Category> getCategory(Model model){
        List<Category> cateList = cateService.list();
        model.addAttribute("cateList",cateList);
        return cateList;
    }
}
