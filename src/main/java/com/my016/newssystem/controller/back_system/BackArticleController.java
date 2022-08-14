package com.my016.newssystem.controller.back_system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my016.newssystem.domain.Article;
import com.my016.newssystem.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/back/article")
@Slf4j
public class BackArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/permitPage")
    public String permitPage(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
                             @RequestParam(value = "uname",required = false)String uname,
                             @RequestParam(value = "cname",required = false)String cname,
                             @RequestParam( value = "title",required = false)String title,
                             Model model, HttpSession session){
        //默认第一页开始，一页20条
        IPage<Article> page = new Page<>(pn, 20);
        //多表查询，status为未审核文章
        IPage<Article> permitStatus = articleService.findAllByStatus(page, uname, cname, title, session,1);
        model.addAttribute("permitStatus",permitStatus);

        //返回总页数
        long permitTotal = permitStatus.getPages();

        model.addAttribute("permitTotal",permitTotal);

        return "/back/article-permit";
    }

    //新闻审核不通过页面
    @RequestMapping("/denyPage")
    public String dengPage(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
                           @RequestParam(value = "uname",required = false)String uname,
                           @RequestParam(value = "cname",required = false)String cname,
                           @RequestParam( value = "title",required = false)String title,
                           Model model, HttpSession session){

        //默认第一页开始，一页20条
        IPage<Article> page = new Page<>(pn, 20);
        //多表查询，status为未审核文章
        IPage<Article> denyStatus = articleService.findAllByStatus(page, uname, cname, title, session,2);
        model.addAttribute("denyStatus",denyStatus);
//        log.info("数据是{}",denyStatus);
        //返回总页数
        long denyTotal = denyStatus.getPages();

        model.addAttribute("denyTotal",denyTotal);

        return "/back/article-deny";
    }

    //后台审核文章否决
    @RequestMapping("/deny")
    @ResponseBody
    public String articleDeny(Integer id){
        Article article = articleService.getById(id);
        article.setStatus(2);
        boolean flag = articleService.updateById(article);
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("data",true);
        return ""+flag;
    }

    //后台审核文章通过
    @RequestMapping("/permit")
    @ResponseBody
    public String articlePermit(Integer id){
        Article article = articleService.getById(id);
        article.setStatus(1);
        boolean flag = articleService.updateById(article);
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("data",true);
//        return map;
        return ""+flag;
    }

    //后台审核查看文章详情页面
    @RequestMapping("/view")
    public String viewArticle(Integer id,Model model){
        Article article = articleService.findMessageId(id);
//        log.info("文章信息是{}",article);
        model.addAttribute("article",article);
        return "/back/article-view";

    }

    //新闻发布页面进行文章删除操作
    @PostMapping("/deleteArticle")
    @ResponseBody
    public String deleteArticle(Integer id){
//        log.info("{}",id);
        articleService.removeById(id);
        return "success";
    }

}
