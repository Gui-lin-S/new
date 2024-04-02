package com.news.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Data
@TableName("t_comment")
public class Comment implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer comid;
    private Integer aid;
    private Integer uid;
    private String content;
    private Date comTime;
    private  Integer illegal;

    @TableField(exist = false)
    private User user;
}
