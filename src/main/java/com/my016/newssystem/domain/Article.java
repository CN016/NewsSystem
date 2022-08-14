package com.my016.newssystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
//import com.baomidou.mybatisplus.annotations.TableField;
//import com.baomidou.mybatisplus.annotations.TableId;
//import com.baomidou.mybatisplus.annotations.TableName;
//import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("t_article")
public class Article implements Serializable ,Comparable<Article>{
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
    private Integer status;  //文章状态，0为待审核，1审核通过，2审核不通过
    private Integer checkNum;
    private String imgSrc;//存放内容出现图片
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editTime;//编辑时间

    private Integer lid;//文章等级

    @TableField(exist = false)//冗余字段，主要为了封装后面的类别
    private Category category;//文章类别
    @TableField(exist = false)
    private User user;//查询用户信息


    @Override
    public int compareTo(Article o) {
        return o.getId() - getId();
    }
}
