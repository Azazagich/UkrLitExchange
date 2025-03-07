package com.example.demo.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomePageWebController {

    @RequestMapping(value = {"/web/ukr-lit-exchange", "/web/ukr-lit-exchange/welcome"})
    public String welcomePage(){
        return "welcome-page";
    }

}
