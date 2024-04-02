package com.news.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("t_user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer uid;
    private String uname;
    private String password;
    private String telephone;
    private String email;
    private String sex;
    private Integer lid;
    private String newsName;
    private String headImage;
    private Integer adRole;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
