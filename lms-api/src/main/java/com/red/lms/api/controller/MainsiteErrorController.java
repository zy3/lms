package com.red.lms.api.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class MainsiteErrorController implements ErrorController {

    @RequestMapping(value = "/error")
    public String handleError(HttpServletResponse response) {
        if (response.getStatus() == HttpStatus.NOT_FOUND.value()) {
            response.setStatus(200);
            return "/index";
        }
        return null;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}