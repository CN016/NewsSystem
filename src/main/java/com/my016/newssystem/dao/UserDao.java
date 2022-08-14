package com.my016.newssystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my016.newssystem.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
