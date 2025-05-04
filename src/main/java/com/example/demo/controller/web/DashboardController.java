package com.example.demo.controller.web;

import com.example.demo.domain.enumeration.ExchangeMethod;
import com.example.demo.service.BookService;
import com.example.demo.service.DashboardService;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.DashboardDTO;
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
    private final DashboardService dashboardService;

    @GetMapping
    public String dashboardPage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int limit,
            Model model
    ) {
        Long userId = userService.getByUsername(userDetails.getUsername()).getId();

        Page<DashboardDTO> dashboardMyRequest = dashboardService.getRequestBookByOwnerId(userId, page, limit);
        Page<DashboardDTO> dashboardRequest = dashboardService.getRequestBooksExceptOwnerId(userId, page, limit);

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

        model.addAttribute("newRequestBook", new DashboardDTO());
        model.addAttribute("books", bookService.getBooksByOwnerId(userId));
        model.addAttribute("statuses", ExchangeMethod.values());

        return "dashboard-create";
    }

    @PostMapping("/perform_create")
    public String addBookDashboard(
            @AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute("newRequestBook") @Valid DashboardDTO dashboardDTO
    ) {
        dashboardService.saveDashboardBookByOwnerId(
                userService.getByUsername(userDetails.getUsername()).getId(),
                dashboardDTO
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
        DashboardDTO DashboardDTO = dashboardService.getById(dashboardBookId);

        model.addAttribute("dashboardBook", DashboardDTO);
        model.addAttribute("books", bookService.getBooksByOwnerId(userId));
        model.addAttribute("statuses", ExchangeMethod.values());

        return "dashboard-edit";
    }

    @PostMapping("/perform_edit")
    public String updateBook(
            @ModelAttribute("dashboardBook") DashboardDTO dashboardDTO
    ){
        dashboardService.update(dashboardDTO.getId(), dashboardDTO);
        return "redirect:/web/ukr-lit-exchange/dashboard";
    }

    @PostMapping("/perform_delete/{dashboardBookId}")
    public String deleteBook(@PathVariable("dashboardBookId") Long dashboardBookId){
        dashboardService.deleteById(dashboardBookId);
        return "redirect:/web/ukr-lit-exchange/dashboard";
    }
}
