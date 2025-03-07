package com.example.demo.controller.web;

import com.example.demo.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class GroupPageWebController {

    private final GroupService groupService;

    @RequestMapping("/web/ukr-lit-exchange/groups")
    public String groupPage(Model model){
        model.addAttribute("groups", groupService.getAll());
        return "group-page";
    }
}
