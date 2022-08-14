package com.my016.newssystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.my016.newssystem.dao.ArticleDao;
import com.my016.newssystem.dao.CategoryDao;
import com.my016.newssystem.dao.UserDao;
import com.my016.newssystem.domain.Article;
import com.my016.newssystem.domain.Category;
import com.my016.newssystem.domain.User;
import com.my016.newssystem.service.ArticleService;
import com.my016.newssystem.utils.CodeRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao,Article> implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Article findMessageId(Integer aid) {
        Article messageId = articleDao.selectById(aid);
        int cid = messageId.getCid();
       // System.out.println(cid);
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("cid",cid);
        Category category =categoryDao.selectOne(wrapper);
        //System.out.println(category);
        messageId.setCategory(category);
        messageId.setUser(userDao.selectById(messageId.getAuthorId()));
       //System.out.println(messageId);
        //messageId.setCategory(categoryDao.selectById(aid));
        messageId.setCheckNum(messageId.getCheckNum()+1);
        articleDao.updateById(messageId);
        return messageId;
    }

    //data=200表示成功
    @Override
    public Map saveArticle(Article article, HttpSession session) {
        Map<String ,Object> map = new HashMap<>();
        User user = (User) session.getAttribute("user");

        if (article.getId()!=null){
            //新闻修改
            Article oriArt = articleDao.selectOne(new QueryWrapper<Article>().eq("id",article.getId()));
            article.setLid(oriArt.getLid());
            article.setCheckNum(oriArt.getCheckNum());
            article.setCreateTime(oriArt.getCreateTime());
            article.setEditTime(new Date());
            //article.setTitle();
            article.setStatus(oriArt.getStatus());
            article.setAuthorId(user.getUid());
            articleDao.updateById(article);
            map.put("data",201);
        }else {
            //添加新闻
            article.setStatus(1);
            article.setAuthorId(user.getUid());
            article.setCreateTime(new Date());
            article.setEditTime(new Date());
            article.setLid(user.getLid());

            int cid = CodeRandom.getIntCode();
            while ( categoryDao.selectById(cid) != null ){
                cid = CodeRandom.getIntCode();
            }
            article.setCid(cid);
            article.setCheckNum(0);
            article.setImgSrc(user.getTelephone());
            categoryDao.insert(new Category(cid,article.getTitle()));
            articleDao.insert(article);
            map.put("data",200);
        }
        return map;
    }

    //新闻搜索
    @Override
    public List<Article> searchArt(HttpSession session, String keyword) {
        User user = (User) session.getAttribute("user");
        Integer lid = 1;
        if (user != null){
            lid = user.getLid();
        }else {
            lid = 1;
        }
        QueryWrapper<Article> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("lid",lid);
        wrapper1.and(wrapper->wrapper.like("title",keyword).or().like("content",keyword));
        List<Article> articles = articleDao.selectList(wrapper1);
        return articles;
    }

    @Override
    public IPage<Article> findAllByStatus(IPage<Article> page, String uname, String cname, String title, HttpSession session, Integer status) {
        User back_user = (User) session.getAttribute("back_user");
        IPage<Article> pageStatus = articleDao.findAllByStatus(page, uname, cname, title, back_user.getLid(),status);
        return pageStatus;
    }

    @Override
    public List<Article> addCateAll() {
        List<Article> articles = articleDao.selectList(null);
        Collections.sort(articles);
        return articles;
    }

    //首页数据
    @Override
    public IPage<Article> findByPage(Integer current, Integer size, HttpSession session) throws Exception {
        IPage<Article> articlePage = new Page<>(current,size);
        //IPage<Article> page = null;
        //User user = (User) session.getAttribute("user"); //获取user对象
        articleDao.selectPage(articlePage,null);
        return articlePage;
    }
}
