package com.my016.newssystem.controller;

//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.baomidou.mybatisplus.plugins.Page;
import com.my016.newssystem.dao.ArticleDao;
import com.my016.newssystem.domain.Article;
import com.my016.newssystem.domain.Category;
import com.my016.newssystem.service.ArticleService;
import com.my016.newssystem.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CateService cateService;

//    private ArticleDao articleDao;
    @RequestMapping( value = {"/","/index"})
   // @ResponseBody
    public String returnIndex(/*@RequestParam(value = "pn" , defaultValue = "1")*/ Model model , Integer pn ,HttpSession session){
        pn = 1;
        List<Article> articleList = articleService.addCateAll();
        Page<Article> articlePage = new Page<>(pn,8);
        Page<Article> pages = articleService.page(articlePage,null);
        model.addAttribute("pages",pages);
        model.addAttribute("list",articleList);
        model.addAttribute("currPn",pn);
        //session.setAttribute("newComArt",articleList); //新评论
        session.setAttribute("newLists",articleList);  //新新闻
        List<Category> categories = cateService.list(null);
        model.addAttribute("categories",categories);
       // articleDao.selectList();
        return "index";
    }

    @RequestMapping("/sss")
    @ResponseBody
    public String hello(){
        return "Hello World";
    }

    //前端请求所有数据
    @RequestMapping("/index/queryAll")
    @ResponseBody
    public Map queryAll(Integer pageNum, Integer pageSize, HttpSession session){
        Map<String , Object> map = new HashMap<>();

        try {
            IPage<Article> page = articleService.findByPage(pageNum,pageSize,session);
            List<Article> addCateList = page.getRecords();
            long total = page.getTotal();
            map.put("data",addCateList);
            map.put("count",total);
            map.put("status",200);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println();
       // System.out.println(map);
        return map;
    }
}
