package com.my016.newssystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my016.newssystem.dao.CommentDao;
import com.my016.newssystem.dao.UserDao;
import com.my016.newssystem.domain.Comment;
import com.my016.newssystem.domain.User;
import com.my016.newssystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;


    @Override
    public Map addComment(Comment comment, HttpSession session) {
        HashMap<String, Object> map = new HashMap<>();
        User user = (User) session.getAttribute("user");
        //是否有登录，有才能评论
        if (user!=null){
//            判断是否有激活账号
            if (user.getActive()==1){
                //查看是否包含敏感词,最小匹配

                    comment.setIllegal(0);

                comment.setUid(user.getUid());
                comment.setComTime(new Date());
                commentDao.insert(comment);
                //标志位，表示成功
                map.put("flag",200);
            }else {
//                未激活
                map.put("flag",401);
            }

        }else {
//            未登录
            map.put("flag",400);
        }
        return map;
    }

    @Override
    public List<User> getComUser(Integer aid) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        List<Comment> com = commentDao.selectList(wrapper.eq("aid", aid));
        List<Integer> userIds = new ArrayList<>();
        List<User> users = new ArrayList<>();
        //是否有评论
        if (com!=null&&!com.isEmpty()){
            userIds=com.stream().map(Comment::getUid).collect(Collectors.toList());
            users= userDao.selectBatchIds(userIds);
        }
        return users;
    }

    @Override
    public IPage<Comment> findComByStatus(IPage<Comment> page, Integer lid, Integer illegal, String uname, Integer userLid) {
        IPage<Comment> comPage = commentDao.findComByStatus(page, lid, illegal, uname, userLid);
        return comPage;
    }
}
