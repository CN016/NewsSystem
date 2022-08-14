package com.my016.newssystem.controller.back_system;

import com.my016.newssystem.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/back")
@Controller
public class BackController {

    @Autowired
    private CateService cateService;

    @RequestMapping("/login")
    public String backLogin(){
        return "/back/login";
    }

    @RequestMapping("/index")
    public String backIndex(){
        return "/back/index";
    }

    @RequestMapping("/welcome")
    public String backWelcome(){
        return "/back/welcome";
    }

}
