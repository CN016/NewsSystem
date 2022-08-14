package com.my016.newssystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my016.newssystem.domain.Article;
import com.my016.newssystem.domain.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {

    Map registerResult(Map<String,String> map, HttpSession session);

    Map loginResult(Map<String,String> map, HttpSession session);

    /*User activeEmail(User user);

    String activeResult(String code,HttpSession session);*/

    Map backLoginResult(Map<String, String> map, HttpSession session);

    List<Article> getFavArt(Integer uid);

    Map alterPass(Map<String,String> map,HttpSession session);
}
