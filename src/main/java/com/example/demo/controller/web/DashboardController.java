package com.example.demo.controller.web;

import com.example.demo.domain.enumeration.ExchangeMethod;
import com.example.demo.service.BookService;
import com.example.demo.service.RequestBoardService;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.BookDTO;
import com.example.demo.service.dto.RequestBoardDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/ukr-lit-exchange/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final UserService userService;
    private final BookService bookService;
    private final RequestBoardService requestBoardService;

    @GetMapping
    public String dashboardPage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int limit,
            Model model
    ) {
        Long userId = userService.getByUsername(userDetails.getUsername()).getId();

        Page<RequestBoardDTO> dashboardMyRequest = requestBoardService.getRequestBookByOwnerId(userId, page, limit);
        Page<RequestBoardDTO> dashboardRequest = requestBoardService.getRequestBooksExceptOwnerId(userId, page, limit);

        model.addAttribute("requestAllBooks", dashboardRequest.getContent());
        model.addAttribute("totalUsersRequestPages", dashboardRequest.getTotalPages());
        model.addAttribute("currentUsersRequestPage", dashboardRequest.getNumber());
        model.addAttribute("totalUserBooks", dashboardRequest.getTotalElements());


        model.addAttribute("requestMyBooks", dashboardMyRequest.getContent());
        model.addAttribute("totalMyBooksPages", dashboardMyRequest.getTotalPages());
        model.addAttribute("currentMyBooksPage", dashboardMyRequest.getNumber());
        model.addAttribute("totalMyBooks", dashboardMyRequest.getTotalElements());

        return "dashboard-page";
    }


    @GetMapping("/create")
    public String addBookDashboardForm(@AuthenticationPrincipal UserDetails userDetails, Model model){
        Long userId = userService.getByUsername(userDetails.getUsername()).getId();

        model.addAttribute("newRequestBook", new RequestBoardDTO());
        model.addAttribute("books", bookService.getBooksByOwnerId(userId));
        model.addAttribute("statuses", ExchangeMethod.values());

        return "dashboard-create";
    }

    @PostMapping("/perform_create")
    public String addBookDashboard(
            @AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute("newRequestBook") @Valid RequestBoardDTO requestBoardDTO
    ) {
        requestBoardService.saveDashboardBookByOwnerId(
                userService.getByUsername(userDetails.getUsername()).getId(),
                requestBoardDTO
        );

        return "redirect:/web/ukr-lit-exchange/dashboard";
    }

    @GetMapping("/edit/{dashboardBookId}")
    public String editBookDashboardForm(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("dashboardBookId") Long dashboardBookId,
            Model model
    ){
        Long userId = userService.getByUsername(userDetails.getUsername()).getId();
        RequestBoardDTO RequestBoardDTO = requestBoardService.getById(dashboardBookId);

        model.addAttribute("dashboardBook", RequestBoardDTO);
        model.addAttribute("books", bookService.getBooksByOwnerId(userId));
        model.addAttribute("statuses", ExchangeMethod.values());

        return "dashboard-edit";
    }

    @PostMapping("/perform_edit")
    public String updateBook(
            @ModelAttribute("dashboardBook") RequestBoardDTO requestBoardDTO
    ){
        requestBoardService.update(requestBoardDTO.getId(), requestBoardDTO);
        return "redirect:/web/ukr-lit-exchange/dashboard";
    }

    @PostMapping("/perform_delete/{dashboardBookId}")
    public String deleteBook(@PathVariable("dashboardBookId") Long dashboardBookId){
        requestBoardService.deleteById(dashboardBookId);
        return "redirect:/web/ukr-lit-exchange/dashboard";
    }
}
