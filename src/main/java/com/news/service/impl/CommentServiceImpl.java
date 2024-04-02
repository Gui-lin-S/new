package com.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news.domain.Comment;
import com.news.domain.User;
import com.news.mapper.CommentMapper;
import com.news.mapper.UserMapper;
import com.news.service.CommentService;
import com.news.utils.SensitivewordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Slf4j
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public Map addComment(Comment comment, HttpSession session) {
        HashMap<String, Object> map = new HashMap<>();
        User user = (User) session.getAttribute("user");
        System.out.println(user);
        //是否有登录，有才能评论
        if (user!=null){
            //查看是否包含敏感词,最小匹配
            boolean isIllegal = SensitivewordUtil.isContaintSensitiveWord(comment.getContent(), 1);
            if (isIllegal){
                //有进行评论过滤
                comment.setIllegal(1);
                comment.setContent(SensitivewordUtil.replaceSensitiveWord(comment.getContent(),1,"*"));
            }else{
                comment.setIllegal(0);
            }
            comment.setUid(user.getUid());
            comment.setComTime(new Date());
            commentMapper.insert(comment);
            //标志位，表示成功
            map.put("flag",200);
        }else {
            map.put("flag",400);
        }
        return map;
    }

    @Override
    public List<User> getComUser(Integer aid) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        List<Comment> com = commentMapper.selectList(wrapper.eq("aid", aid));
        List<Integer> userIds = new ArrayList<>();
        List<User> users = new ArrayList<>();
        //是否有评论
        if (com!=null&&!com.isEmpty()){
            userIds=com.stream().map(Comment::getUid).collect(Collectors.toList());
            users= userMapper.selectBatchIds(userIds);
        }
        return users;
    }

    @Override
    public IPage<Comment> findComByStatus(IPage<Comment> page, Integer lid, Integer illegal, String uname, Integer userLid) {
        IPage<Comment> comPage = commentMapper.findComByStatus(page, lid, illegal, uname, userLid);
        return comPage;
    }
}
