package com.my016.newssystem.controller;

import com.my016.newssystem.domain.Category;
import com.my016.newssystem.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/common")
public class CommonController {

    @Autowired
    CateService cateService;

    @RequestMapping("/getCategory")
    @ResponseBody
    public List<Category> getCategory(Model model){
        List<Category> categories = cateService.list();
        model.addAttribute("cateList",categories);
        return categories;
    }
}
