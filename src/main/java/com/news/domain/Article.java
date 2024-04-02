package com.news.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Data
@TableName("t_article")
public class Article implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private Integer cid;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private int authorId;
    private Integer status;
    private Integer checkNum;
    private String imgSrc;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editTime;

    private Integer lid;

    @TableField(exist = false)
    private Category category;
    @TableField(exist = false)
    private User user;
}
