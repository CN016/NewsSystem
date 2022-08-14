package com.my016.newssystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my016.newssystem.dao.FavoriteDao;
import com.my016.newssystem.domain.Favorite;
import com.my016.newssystem.service.FavoriteService;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteDao, Favorite> implements FavoriteService {
}
