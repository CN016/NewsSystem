package com.my016.newssystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my016.newssystem.dao.ArticleDao;
import com.my016.newssystem.dao.UserDao;
import com.my016.newssystem.domain.Article;
import com.my016.newssystem.domain.User;
import com.my016.newssystem.service.UserService;
import com.my016.newssystem.utils.CodeRandom;
import com.my016.newssystem.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ArticleDao articleDao;


    //注册
    @Override
    public Map registerResult(Map<String, String> map, HttpSession session) {
        //map是用户从页面发送过来的注册信息
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        User user = new User();
        user.setTelephone(map.get("telephone"));
        user.setUname(map.get("uname"));
        user.setSex(map.get("sex"));
        user.setEmail(map.get("email"));
        user.setPassword(map.get("password"));
        user.setNewsName(map.get("news_name"));
        String checkCode =map.get("checkcode");
        Map<String,String> map1 = new HashMap<>();
        String msg="";
        Integer status;
        if (checkCode.equals(checkcode_server)){
            msg=null;
            status=200;
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uname",user.getUname());
            User user1 =userDao.selectOne(queryWrapper);
            //数据库不存在
            if (user1 == null){
                user.setPassword(MD5Utils.MD5EncodeUtf8(user.getPassword()));
                //默认头像
                user.setHeadImage("/images/none.jpg");
                user.setCreateTime(new Date());
                user.setCode(CodeRandom.getCode());
                user.setActive(1);
                user.setLid(1);
                userDao.insert(user);
            }else {
                status = 401;
                msg = "用户名已存在，请选择 新的用户名";
            }

        }else {
            status = 400;
            msg = "验证码输入错误，请重新输入";
        }
        map.put("msg",msg);
        map.put("status",status.toString());
        return map;
    }

    //返回登陆结果
    @Override
    public Map loginResult(Map<String, String> map, HttpSession session) {
        String uname = map.get("uname");
        String password = MD5Utils.MD5EncodeUtf8(map.get("password"));
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("uname",uname).eq("password",password);
        User user = userDao.selectOne(wrapper);
        String checkcode = map.get("checkcode");

        //验证码
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        HashMap<String,Object> map1 = new HashMap<>();

        Integer status;
        String msg="";

        if (checkcode.equals(checkcode_server)){
            msg = null;
            if (user == null){
                msg = "用户名或密码输入错误，请重新输入";
                status=401;
            } else {
                status=200;
                Integer active = user.getActive();
                if (active==1){
                    msg="登录成功，即将返回首页";
                }else {
                    msg = "登录成功，账号尚未激活，请进行激活验证解封功能";
                }
                session.setAttribute("user",user);
            }
        }else {
            status=400;
            msg="验证码输入错误，请重新输入";
        }
        map1.put("msg",msg);
        map1.put("status",status.toString());
        return map1;

    }

   /* @Override
    public User activeEmail(User user) {
        return null;
    }*/

   /* //返回激活结果
    @Override
    public String activeResult(String code, HttpSession session) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("code",code);
        User findByCode = userDao.selectOne(wrapper);
        String msg = null;
        if (findByCode!=null){
            findByCode.setActive(1);
            boolean flag = updateById(findByCode);

            if (flag){
                msg="激活成功，请返回<a href='/user/login'>登录</a>或继续浏览<a href='/index'>首页</a>";
                User user = (User) session.getAttribute("user");
                if (user != null){
                    session.setAttribute("user",findByCode);
                }
            }else {
                msg="激活失败，请联系管理员激活";
            }
        }else {
            msg="激活失败，请联系管理员激活";
        }
        return msg;
    }*/

    //后端查询结果
    @Override
    public Map backLoginResult(Map<String, String> map, HttpSession session) {
        String uname = map.get("uname");
        String password = MD5Utils.MD5EncodeUtf8(map.get("password"));
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("uname",uname).eq("password",password);
        User back_user = userDao.selectOne(wrapper);
        String captcha = map.get("captcha");//取出验证码
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        HashMap<String, Object> map1 = new HashMap<>();
        Integer status;
        String msg = "";
        if (captcha.equals(checkcode_server)){
            if (back_user != null){
                if (back_user.getLid()<2){
                    status = 401;
                    msg = "该用户权限不够，不能进入后台管理系统";
                }else {
                    status = 200;
                    msg = "登录成功，即将进入后台管理系统";
                    session.setAttribute("back_user",back_user);
                }
            }else {
                status = 400;
                msg="用户名或密码错误，请重检查是否输入有误";
            }
        }else {
            status=402;
            msg="验证码不正确，请重新输入验证码";
        }
        map1.put("status",status);
        map1.put("msg",msg);
        return map1;
    }

    @Override
    public List<Article> getFavArt(Integer uid) {
        return null;
    }

    //修改密码
    @Override
    public Map alterPass(Map<String, String> map, HttpSession session) {
        Map<String , Object> res_map = new HashMap<>();
        String cur_pass = map.get("cur_pass");
        String password = map.get("password");
        User user = (User) session.getAttribute("user");

        if (MD5Utils.MD5EncodeUtf8(cur_pass).equals(user.getPassword())){
            user.setPassword(MD5Utils.MD5EncodeUtf8(password));
            session.setAttribute("user",user);

            int i = userDao.updateById(user);
            log.info("{}",i);
            res_map.put("flag",200);
        }else {
            res_map.put("flag",400);
        }
        return res_map;
    }
}
