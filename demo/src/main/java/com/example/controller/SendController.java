package com.example.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@RestController
@RequestMapping("weixin/send")
public class SendController {

    @RequestMapping("/sendTemplateData2")
    public void sendTemplateData(String openID) {
        System.out.println(openID);
    }
}
