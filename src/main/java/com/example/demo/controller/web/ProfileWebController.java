package com.example.demo.controller.web;

import com.example.demo.service.UserService;
import com.example.demo.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/ukr-lit-exchange/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileWebController {

    private final UserService userService;

    @GetMapping
    public String profilePage(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        UserDTO userDTO = userService.getById(
                userService.getByUsername(userDetails.getUsername()).getId());
        model.addAttribute("user", userDTO);
        return "profile-page";
    }


    @GetMapping("/edit")
    public String editProfileInfoForm(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        UserDTO userDTO = userService.getById(
                userService.getByUsername(userDetails.getUsername()).getId());
        model.addAttribute("user", userDTO);
        return "profile-edit";
    }


    @PostMapping("/perform_edit")
    public String updateUserProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute("user") UserDTO userDTO
    ) {
        userService.update(
                userService.getByUsername(userDetails.getUsername()).getId(), userDTO);
        return "redirect:/web/ukr-lit-exchange/profile";
    }



    @GetMapping("admin/{userId}")
    public String profilePage(
            Model model,
            @PathVariable("userId") Long id
    ) {
        UserDTO userDTO = userService.getById(id);
        model.addAttribute("user", userDTO);
        return "profile-page";
    }
}
