package com.news.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 归林
 * @date 2024/4/2
 */
@Data
@TableName("t_category")
public class Category implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer cid;
    private String cname;
}
