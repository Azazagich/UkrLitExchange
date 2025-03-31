package com.example.demo.controller.web;

import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MyLibraryWebController {

    private final BookService bookService;

    @RequestMapping("/web/ukr-lit-exchange/my-library/{id}")
    public String libraryPage(Model model, @PathVariable Long id){
        model.addAttribute("userBooks", bookService.getBooksByOwnerId(id));
        return "my-library-page";
    }
}
