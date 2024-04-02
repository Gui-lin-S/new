package com.news.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("t_favorite")
public class Favorite implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer fid;
    private Integer uid;
    private Integer aid;
    private Date addTime;
}
