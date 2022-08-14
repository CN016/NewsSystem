package com.my016.newssystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@TableName("t_level")
@Data
public class Level implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer lid;
    private String lname;
    private Integer level;
}
