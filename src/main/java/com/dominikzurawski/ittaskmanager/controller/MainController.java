package com.dominikzurawski.ittaskmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }

}
