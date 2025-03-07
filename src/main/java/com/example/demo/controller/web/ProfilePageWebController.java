package com.example.demo.controller.web;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class ProfilePageWebController {

    private final UserService userService;

    @GetMapping("/web/ukr-lit-exchange/profile/{id}")
    public String profilePage(Model model, @PathVariable Long id){
        UserDTO userDTO = userService.getById(id);
        model.addAttribute("user", userDTO);
        return "profile-page";
    }
}
