package com.red.lms.api.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {

    @GetMapping({"/", "/index", "index.html"})
    public String index() {
        return "/index";   // 或 return index.html
    }
}