package com.example.demo.controller.web;

import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.BookDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/ukr-lit-exchange/my-library")
@RequiredArgsConstructor
@Slf4j
public class MyLibraryWebController {

    private final BookService bookService;
    private final UserService userService;

    @GetMapping
    public String libraryPage(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int limit
    ){
        Long userId = userService.getByUsername(userDetails.getUsername()).getId();
        Page<BookDTO> booksPage = bookService.getBooksByOwnerId(userId, page, limit);
        model.addAttribute("userBooks", booksPage.getContent());
        model.addAttribute("totalPages", booksPage.getTotalPages());
        model.addAttribute("currentPage", booksPage.getNumber());
        model.addAttribute("totalBooks", booksPage.getTotalElements());


        return "my-library-page";
    }

    @GetMapping("/save")
    public String addBookForm(Model model){
        model.addAttribute("newBook", new BookDTO());
        return "my-library-add";
    }

    @PostMapping("/perform_save")
    public String addBook(
            @AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute("newBook") @Valid BookDTO bookDTO,
            BindingResult bindingResult
    ){
        try {
            bookService.saveBookByOwnerId(
                    userService.getByUsername(userDetails.getUsername()).getId(),
                    bookDTO);
        } catch (Exception e) {
            log.error("Error saving book: {}", e.getMessage());
            bindingResult.reject("book.save.error",
                    "Не вдалося зберегти книгу. Спробуйте ще раз.");
            return "my-library-add";
    }
        return  "redirect:/web/ukr-lit-exchange/my-library";
    }

    @GetMapping("/edit/{bookId}")
    public String editBookForm(
            @PathVariable("bookId") Long id,
            Model model
    ){
        BookDTO bookDTO = bookService.getById(id);
        model.addAttribute("book", bookDTO);
        return "my-library-edit";
    }

    @PostMapping("/perform_edit")
    public String updateBook(
            @ModelAttribute("book") BookDTO bookDTO
    ){
        bookService.update(bookDTO.getId(), bookDTO);
        return "redirect:/web/ukr-lit-exchange/my-library";
    }

    @PostMapping("/perform_delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") Long id){
        bookService.deleteById(id);
        return "redirect:/web/ukr-lit-exchange/my-library";
    }

}
