package com.my016.newssystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
//import com.baomidou.mybatisplus.annotations.TableId;
//import com.baomidou.mybatisplus.annotations.TableName;
//import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;


@Data
@TableName("t_article_image")
public class ArtImage  implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer imageId;
    private String imageSrc;
    private Integer artId;
}
