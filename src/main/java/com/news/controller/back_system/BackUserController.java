package com.news.controller.back_system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.news.domain.User;
import com.news.service.UserService;
import com.news.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Slf4j
@Controller
@RequestMapping("/back/user")
public class BackUserController {
    @Autowired
    UserService userService;

    /**
     * 后台用户管理请求
     * @param map
     * @param session
     * @return
     */
    @PostMapping({"/loginResult"})
    @ResponseBody
    public Map sendLoginResult(@RequestBody Map<String,String> map, HttpSession session){
        Map resultMap = userService.backLoginResult(map, session);

        return resultMap;
    }

    /**
     * 用户退出
     * @param session
     * @return
     */
    @RequestMapping("/quit")
    public String quit(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    /**
     * 返回用户列表，如有条件则添加
     * @param pn
     * @param uname
     * @param lid
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/manage")
    public String userManage(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
                             @RequestParam(value = "uname",required = false)String uname,
                             @RequestParam(value = "lid",required = false)Integer lid,
                             Model model, HttpSession session){

        //默认第一页开始，一页20条
        IPage<User> page = new Page<>(pn, 10);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //条件筛选
        if (StringUtils.isNotBlank(uname)){
            log.info("1");
            queryWrapper.like("uname",uname);
        }
        if (lid!=null){
            queryWrapper.eq("lid",lid);
            log.info("2");
        }
        User back_user = (User) session.getAttribute("back_user");
        //展示小于用户等级的数据
        queryWrapper.lt("lid",back_user.getLid());
        IPage<User> userIPage = userService.page(page, queryWrapper);
        //返回数据和总页数
        model.addAttribute("userIPage",userIPage);
        long pages = userIPage.getPages();
        model.addAttribute("pages",pages);

        return "back/user-manage";
    }

    /**
     * 添加新用户页面
     * @return
     */
    @RequestMapping("/addUser")
    public String addUser(){
        return "back/user-add";
    }

    /**
     * 添加新用户和编辑结果的接收与返回
     * @param user
     * @return
     */
    @PostMapping("/addOne")
    @ResponseBody
    public Map addOneResult(@RequestBody User user){
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("uname",user.getUname());
        //判断是否存在相同的人
        User one = userService.getOne(wrapper);
        String msg=null;
        int status;
        if (one!=null){
            status=400;
            msg="该用户账号已存在，请选择其他的用户账号";
        }else{
            status=200;
            msg="添加用户成功";
            user.setCreateTime(new Date());
            user.setNewsName("暂未设置用户昵称");
            userService.save(user);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("status",status);
        map.put("msg",msg);
        return map;
    }

    /**
     * 用户删除
     * @param uid
     * @return
     */
    @PostMapping("/deleteUser")
    @ResponseBody
    public String returnDelResult(Integer uid){
        userService.removeById(uid);
        return "success";
    }

    /**
     * 用户编辑
     * @param uid
     * @param model
     * @return
     */
    @RequestMapping("/editUser")
    public String editUser(Integer uid,Model model){
        User userInfo = userService.getById(uid);
        model.addAttribute("userInfo",userInfo);
        return "back/user-info";
    }

    /**
     * 用户编辑页面提交结果返回
     * @param user
     * @param model
     * @return
     */
    @PostMapping("/editResult")
    @ResponseBody
    public Map editResult(@RequestBody User user,Model model){
        if (user.getPassword().length() <= 18){
            user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        }
        //获取原来的用户昵称
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        User byId = userService.getById(user.getUid());
        user.setNewsName(byId.getNewsName());
        boolean b = userService.updateById(user);
        Integer status;
        String msg;
        if (b){
            status=200;
            msg="用户信息修改成功";
        }else {
            status=400;
            msg="出现错误，请稍后尝试";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("status",status);
        map.put("msg",msg);
        return map;
    }

    /**
     * 用户数据导出excel
     * @return
     */
    @PostMapping("/exportUserData")
    @ResponseBody
    public List<User> exportUserData(){
        List<User> userList = userService.list();
        return userList;
    }
}
