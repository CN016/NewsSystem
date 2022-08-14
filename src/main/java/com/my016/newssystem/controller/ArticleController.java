package com.my016.newssystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.my016.newssystem.domain.Article;
import com.my016.newssystem.domain.Category;
import com.my016.newssystem.domain.Comment;
import com.my016.newssystem.domain.User;
import com.my016.newssystem.service.ArticleService;
import com.my016.newssystem.service.CateService;
import com.my016.newssystem.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CateService cateService;

    @Autowired
    private CommentService commentService;

    @RequestMapping("article_pub")
    public String article_Pub(@RequestParam(value = "id",required = false)Integer id,Model model) {
        Article byId = articleService.getById(id);
        model.addAttribute("byId",byId);

        List<Category> categories = cateService.list();
        model.addAttribute("categories",categories);
        return "article_pub";
    }

    //上传图片等资源文件
    @ResponseBody
    @RequestMapping(value = "/upload/uploadFile")
    public Map uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        if (file != null) {
//            String webapp = request.getSession().getServletContext().getRealPath("/");
            //存放到项目静态资源下
            String webapp = "src/main/resources/";
            try {
                //图片名字
                String substring = file.getOriginalFilename();
//                System.out.println(substring);
                //使用uuid替代原来名字
                String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                // 图片的路径+文件名称
//                String fileName = "/static/upload/" + substring;
                //使用uuid上传将上传的图片重命名，但是遇到改名之后上传较慢，需要等待传输才能回显
                String uuidName = uuid + "." + substring.substring(substring.lastIndexOf(".") + 1);
//                System.out.println(uuidName);
                String fileName = "/static/upload/" + uuidName;
//                System.out.println(fileName);
                // 图片的在服务器上面的物理路径
                File destFile = new File(webapp, fileName);
//                log.info("真实路径{}",destFile);
                // 生成upload目录
                File parentFile = destFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();// 自动生成upload目录
                }
                // 把上传的临时图片，复制到当前项目的webapp路径
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(destFile));
                map = new HashMap<>();
                map2 = new HashMap<>();
                map.put("code", 0);//0表示成功，1失败
                map.put("msg", "上传成功");//提示消息
                map.put("data", map2);
//                map2.put("src", fileName);//图片url
//                map2.put("src", "/upload/" + substring);//图片url
                map2.put("src", "/upload/"+uuidName);//图片url
//                map2.put("title", substring);//图片名称，这个会显示在输入框里
                map2.put("title", uuidName);//图片名称，这个会显示在输入框里
                log.info("图片地址为{}",uuidName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    //文章保存
    @PostMapping("/articleSave")
    @ResponseBody
    public Map articleSave(@RequestBody Article article, HttpSession session) {

        Map map1 = articleService.saveArticle(article, session);

        return map1;
    }

    @GetMapping("/cate/CateArticle/{cid}")
    public String cateArticle(@PathVariable("cid") Integer cid , Model model){
        Category cateById = cateService.getById(cid);
        model.addAttribute("cateById",cateById);
        return "article";
    }

    @PostMapping("/article/search")
    public String search(String keyword, Model model, HttpSession session){
        model.addAttribute("keyword",keyword);
        List<Article> articles = articleService.searchArt(session,keyword);
        model.addAttribute("articles",articles);
        return "search";
    }

    //点击文章跳转
    @GetMapping("/article/details/{id}")
    public String findArticle( @PathVariable("id") Integer id , Model model ){

        Article articleById = articleService.findMessageId(id);
        //获取该新闻下评论
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        List<Comment> commentList = commentService.list(wrapper.eq("aid", id).orderByAsc("com_time"));
        //获取相关评论的用户信息
        List<User> comUser = commentService.getComUser(id);

        model.addAttribute("articleById",articleById);
        model.addAttribute("commentList",commentList);
        model.addAttribute("comUser",comUser);
        return "article_details";
    }
}
