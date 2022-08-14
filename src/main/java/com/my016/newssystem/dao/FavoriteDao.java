package com.my016.newssystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my016.newssystem.domain.Favorite;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FavoriteDao extends BaseMapper<Favorite> {
}
