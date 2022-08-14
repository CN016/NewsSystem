package com.my016.newssystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@Data
@TableName("t_category")
public class Category implements Serializable {

    private Integer cid;
    private String cname;

    public Category(Integer cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }
}
