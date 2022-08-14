package com.my016.newssystem.service;

//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
//import com.baomidou.mybatisplus.service.IService;
import com.my016.newssystem.domain.Article;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface ArticleService extends IService<Article> {

    Article findMessageId(Integer aid);

    Map saveArticle(Article article , HttpSession httpSession);

    List<Article> searchArt(HttpSession session, String keyword);

    IPage<Article> findAllByStatus(IPage<Article> page,String uname,String cname,String title,HttpSession session,Integer status);

    //查询所有新闻，添加了分类名字
    List<Article> addCateAll();

    //首页查询所有数据，分页处理
    IPage<Article> findByPage(Integer current, Integer size, HttpSession session) throws Exception;
}
