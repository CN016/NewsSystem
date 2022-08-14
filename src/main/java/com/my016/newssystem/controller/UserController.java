package com.my016.newssystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.my016.newssystem.domain.Article;
import com.my016.newssystem.domain.Comment;
import com.my016.newssystem.domain.Favorite;
import com.my016.newssystem.domain.User;
import com.my016.newssystem.service.ArticleService;
import com.my016.newssystem.service.CommentService;
import com.my016.newssystem.service.FavoriteService;
import com.my016.newssystem.service.UserService;
import com.my016.newssystem.utils.CheckCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FavoriteService favoriteService;

    @RequestMapping("/register")
    public String register(Model model){
        return "register";
    }

    @RequestMapping("/register/checkCode")
    @ResponseBody
    public void getCheck(HttpServletResponse response , HttpServletRequest request){
        CheckCodeUtil.getCheckCode(request,response);
    }

    //注册页面提交返回表单结果
    @PostMapping("/register/result")
    @ResponseBody
    public Map sendRegisterResult(@RequestBody Map<String,String> map1 ,
                                  HttpSession session){
        Map map = userService.registerResult(map1,session);
        return map;
    }

    @RequestMapping("/login")
    public String userLogin(Model model){
        return "login";
    }

    @PostMapping("/login/result")
    @ResponseBody
    public Map sendLoginResult(@RequestBody Map<String,String> map1 ,
                               HttpSession session){
        Map map = userService.loginResult(map1,session);
        log.info("用户属性{}",map1);
        log.info("map{}",map);
        return map;
    }

    @RequestMapping("/quit")
    public String userQuit(HttpSession session)
    {
        //销毁保存的session
        session.invalidate();
        return "redirect:/index";
    }

    //激活结果
    @RequestMapping("/active")
    @ResponseBody
    public String userActive(@RequestParam("code")String code,HttpSession session)
    {
        String msg = "激活成功";
        return msg;
    }

    //用户中心，返回用户发帖和帖子收藏以及评论
    @RequestMapping("/user_center")
    public String user_center(Model model,HttpSession session) {
        //返回用户发表的新闻
        User user =(User) session.getAttribute("user");
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("author_id",user.getUid()).orderByDesc("create_time");
        List<Article> list = articleService.list(wrapper);
        model.addAttribute("lists",list);

        //返回用户的收藏
        List<Article> favArts = userService.getFavArt(user.getUid());
        model.addAttribute("favArts",favArts);
        //需要返回添加时间
        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",user.getUid()).orderByDesc("add_time");
        List<Favorite> favorites = favoriteService.list(queryWrapper);
        model.addAttribute("favorites",favorites);
        //返回用户评论
        QueryWrapper<Comment> comWrapper = new QueryWrapper<>();
        comWrapper.eq("uid",user.getUid()).orderByDesc("com_time");
        List<Comment> commentList = commentService.list(comWrapper);
        model.addAttribute("commentList",commentList);
        return "user_center";
    }

    //个人设置页面
    @RequestMapping("/set")
    public String set(Model model,HttpSession session) {
        User user =(User) session.getAttribute("user");
        model.addAttribute("user",user);
        return "set";
    }

    //用户手动点击激活
    /*@RequestMapping("per_active")
    @ResponseBody
    public  Map personActive(HttpSession session){
        User user = (User) session.getAttribute("user");
        user=userService.activeEmail(user);
        //将激活码写进去数据库
        userService.updateById(user);
        HashMap<String, Object> map = new HashMap<>();
        map.put("flag",200);
        return map;
    }*/

    //进行用户个人新闻的删除
    @RequestMapping("/delArt")
    @ResponseBody
    public Map delArt(Integer aid,HttpSession session){
        log.info("{}", aid);
        boolean b = articleService.removeById(aid);
        HashMap<String, Object> map = new HashMap<>();
        if (b){
            map.put("flag",200);
            map.put("msg","删除成功");
        }else {
            map.put("flag",400);
            map.put("msg","删除失败，请稍后尝试");
        }
        return map;
    }

    //进行用户收藏夹的删除
    @RequestMapping("/delFav")
    @ResponseBody
    public Map delFavorite(Integer aid,HttpSession session){
        User user =(User) session.getAttribute("user");
        QueryWrapper<Favorite> wrapper = new QueryWrapper<>();
        wrapper.eq("aid",aid).eq("uid",user.getUid());
        boolean b = favoriteService.remove(wrapper);
        HashMap<String, Object> map = new HashMap<>();
        if (b){
            map.put("flag",200);
            map.put("msg","删除成功");
        }else {
            map.put("flag",400);
            map.put("msg","删除失败，请稍后尝试");
        }
        return map;
    }


    //进行用户评论的删除
    @RequestMapping("/delCom")
    @ResponseBody
    public Map delComment(Integer comid,HttpSession session){
        User user =(User) session.getAttribute("user");
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("comid",comid);
        boolean b = commentService.remove(wrapper);
        HashMap<String, Object> map = new HashMap<>();
        if (b){
            map.put("flag",200);
            map.put("msg","删除成功");
        }else {
            map.put("flag",400);
            map.put("msg","删除失败，请稍后尝试");
        }
        return map;
    }

    //上传图片等资源文件
    @ResponseBody
    @RequestMapping(value = "/uploadHeadImg")
    public Map uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
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
                String fileName = "/static/upload_headImg/" + uuidName;
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
                map2.put("src", "/upload_headImg/"+uuidName);//图片url
//                map2.put("title", substring);//图片名称，这个会显示在输入框里
                map2.put("title", uuidName);//图片名称，这个会显示在输入框里
                log.info("图片地址为{}",uuidName);
                //将用户头像保存到数据库
                User user = (User) session.getAttribute("user");
                user.setHeadImage("/upload_headImg/"+uuidName);
                userService.updateById(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    //修改用户基本信息
    @PostMapping("/alterInfo")
    @ResponseBody
    public Map alterUserInfo(@RequestBody User user,HttpSession session){
//        log.info("用户信息{}",user);
        Map<String, Object> map = new HashMap<>();
        //从session取出已有数据然后替换，保存到数据库
        User sessionUser = (User) session.getAttribute("user");
        sessionUser.setTelephone(user.getTelephone());
        sessionUser.setEmail(user.getEmail());
        sessionUser.setSex(user.getSex());
        sessionUser.setNewsName(user.getNewsName());
        //更新用户信息
        boolean flag = userService.updateById(sessionUser);
        if (flag){
            //修改session
            session.setAttribute("user",sessionUser);
            map.put("flag",200);
        }

        return map;
    }

    //修改用户密码
    @PostMapping("/alterPassword")
    @ResponseBody
    public Map alterPassword(@RequestBody Map<String,String> map1
            ,HttpSession session){
//        log.info("修改密码{}",map1);
        Map map = userService.alterPass(map1, session);
        return map;
    }



}
