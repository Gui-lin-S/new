package com.news.controller.back_system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.news.domain.Category;
import com.news.service.CateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Slf4j
@Controller
@RequestMapping("/back/")
public class BackCateController {
    @Autowired
    CateService cateService;

    /**
     * 分类页管理
     * @param model
     * @return
     */
    @RequestMapping("/cate")
    public String cateManage(Model model){
        List<Category> list = cateService.list();
        int count = cateService.count();
        model.addAttribute("cates",list);
        model.addAttribute("count",count);
        return "back/cate-manage";
    }

    /**
     * 分类页添加结果反馈
     * @param category
     * @return
     */
    @RequestMapping("/cateadd")
    @ResponseBody
    public Map<String, Object> cateAdd(@RequestBody Category category){
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        //添加限制条件为1条，多个结果集会报错
        queryWrapper.eq("cname",category.getCname()).last("LIMIT 1");
        Category one = cateService.getOne(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        //判断分类是否存在
        if (one==null){
            cateService.save(category);
            map.put("code",200);
        }else {
            map.put("code",410);
        }
        return map;
    }

    /**
     * 分类的删除
     * @param cid
     * @return
     */
    @RequestMapping("/cate/delete")
    @ResponseBody
    public String cateDelete(Integer cid){
        Category byId = cateService.getById(cid);
        boolean result=false;
        try {

            result = cateService.removeById(cid);
            log.info("结果是{}",result);
            return ""+result;
        }catch (Exception e){
            e.printStackTrace();

        }
        return ""+result;
    }

    /**
     * 分类的修改
     * @param category
     * @return
     */
    @RequestMapping("/cate_update")
    @ResponseBody
    public Map<String, Object> cateUpdate(@RequestBody Category category){
        log.info("修改的分类为:{}",category);
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("cname",category.getCname());
        Category one = cateService.getOne(wrapper);
        boolean flag;
        //判断修改分类是否存在
        if (one==null){
            cateService.updateById(category);
            flag=true;
        }else {
            flag = false;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("flag",flag);
        return map;
    }
}
