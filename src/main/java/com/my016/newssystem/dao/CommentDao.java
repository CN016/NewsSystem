package com.my016.newssystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.my016.newssystem.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentDao extends BaseMapper<Comment> {

    IPage<Comment> findComByStatus(IPage<Comment> page, @Param("lid") Integer lid,
                                   @Param("illegal") Integer illegal, @Param("uname") String uname,
                                   @Param("userLid")Integer userLid);
}
