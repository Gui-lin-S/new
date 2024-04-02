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
@TableName("t_level")
public class Level implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer lid;
    private String lname;
    private Integer level;
}
