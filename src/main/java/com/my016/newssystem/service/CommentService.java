package com.my016.newssystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.my016.newssystem.domain.Comment;
import com.my016.newssystem.domain.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface CommentService extends IService<Comment> {
    Map addComment(Comment comment, HttpSession session);

    List<User> getComUser (Integer aid);

    IPage<Comment> findComByStatus(IPage<Comment> page, Integer lid , Integer illegal, String uname, Integer userLid );
}
