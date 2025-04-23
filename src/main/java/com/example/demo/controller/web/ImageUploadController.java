package com.example.demo.controller.web;

import com.example.demo.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @PostMapping("/uploadAvatar")
    public String uploadAvatarImage(
            @RequestParam("image") MultipartFile file,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {
        imageUploadService.uploadAvatarImage(file, userDetails.getUsername());
        return "redirect:/web/ukr-lit-exchange/profile";
    }

    @PostMapping("/uploadBook/{bookId}")
    public String uploadBookImage(
            @RequestParam("image") MultipartFile file,
            @PathVariable("bookId") Long id
    ) throws IOException {
        imageUploadService.uploadBookImage(file, id);
        return "redirect:/web/ukr-lit-exchange/my-library";
    }
}
