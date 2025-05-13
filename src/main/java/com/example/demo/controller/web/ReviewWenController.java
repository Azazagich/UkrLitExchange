package com.example.demo.controller.web;

import com.example.demo.service.RequestService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.RequestDTO;
import com.example.demo.service.dto.ReviewDTO;
import com.example.demo.service.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/ukr-lit-exchange/feedback")
@RequiredArgsConstructor
public class ReviewWenController {

    private final ReviewService reviewService;
    private final RequestService requestService;
    private final UserService userService;


    @GetMapping("/new")
    public String showReviewForm(@RequestParam Long requestId, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserDTO currentUser = userService.getByUsername(userDetails.getUsername());
        RequestDTO request = requestService.getById(requestId);

        UserDTO reviewer = currentUser;
        UserDTO user = (reviewer.getId().equals(request.getSender().getId()))
                ? request.getReceiver() : request.getSender();

        ReviewDTO reviewDTO = ReviewDTO.builder()
                .user(user)
                .reviewer(reviewer)
                .build();

        model.addAttribute("review", reviewDTO);
        model.addAttribute("requestId", requestId);
        return "review-form";
    }

    @PostMapping("/save")
    public String saveReview(@ModelAttribute("review") @Valid ReviewDTO reviewDTO,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "review-form";
        }

        Long senderId = reviewDTO.getReviewer().getId();
        Long receiverId = reviewDTO.getUser().getId();

        if (reviewService.alreadyReviewed(senderId, receiverId)) {
            model.addAttribute("errorMessage", "Ви вже залишили відгук про цього користувача.");
            return "review-form";
        }

        reviewService.save(reviewDTO);
        return "redirect:/web/ukr-lit-exchange/request";
    }
}
