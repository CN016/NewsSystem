package com.my016.newssystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my016.newssystem.dao.CateDao;
import com.my016.newssystem.domain.Category;
import com.my016.newssystem.service.CateService;
import org.springframework.stereotype.Service;

@Service
public class CateServiceImpl extends ServiceImpl<CateDao, Category> implements CateService {
}
