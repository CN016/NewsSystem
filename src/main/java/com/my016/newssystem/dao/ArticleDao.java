package com.my016.newssystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.my016.newssystem.domain.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleDao extends BaseMapper<Article> {
     //findAllById();
     IPage<Article> findAllByStatus(IPage<Article> page, @Param("uname") String uname, @Param("cname") String cname,
                                    @Param("title") String title, @Param("lid") Integer lid, @Param("status") Integer status);
}
