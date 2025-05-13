package com.example.demo.controller.web;

import com.example.demo.service.RequestService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.ReviewDTO;
import com.example.demo.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@RequestMapping("/web/ukr-lit-exchange/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileWebController {

    private final UserService userService;
    private final ReviewService reviewService;
    private final RequestService requestService;

    @GetMapping
    public String profilePage(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        UserDTO userDTO = userService.getByUsername(userDetails.getUsername());
        List<ReviewDTO> reviewsDTO = reviewService.getReviewsByUserId(userDTO.getId());
        model.addAttribute("user", userDTO);
        model.addAttribute("avatar_url", userDTO.getAvatarUrl());
        model.addAttribute("reviews", reviewsDTO);
        model.addAttribute("completedExchange", requestService.getСompletedOrders(userDTO.getId()).size());
        model.addAttribute("sendRequests", requestService.getSentOrders(userDTO.getId()).size());
        model.addAttribute("allRequests", requestService.getAllUserOrders(userDTO.getId()).size());

        return "profile-page";
    }


    @GetMapping("/edit")
    public String editProfileInfoForm(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        UserDTO userDTO = userService.getByUsername(userDetails.getUsername());
        model.addAttribute("user", userDTO);
        return "profile-edit";
    }


    @PostMapping("/perform_edit")
    public String updateUserProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute("user") UserDTO userDTO
    ) {
        userService.update(
                userService.getByUsername(userDetails.getUsername()).getId(),
                userDTO);
        return "redirect:/web/ukr-lit-exchange/profile";
    }


    @GetMapping("admin/{userId}")
    public String profilePage(
            @PathVariable("userId") Long id,
            Model model
    ) {
        UserDTO userDTO = userService.getById(id);
        model.addAttribute("user", userDTO);
        return "profile-page";
    }
}
