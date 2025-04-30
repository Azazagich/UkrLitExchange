package com.example.demo.controller.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/web/ukr-lit-exchange", "/web/ukr-lit-exchange/welcome"})
@Slf4j
public class WelcomeWebController {

    @GetMapping
    public String welcomePage(){
        return "welcome-page";
    }
}
