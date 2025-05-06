package com.example.demo.controller.web;


import com.example.demo.domain.User;
import com.example.demo.domain.enumeration.RequestStatus;
import com.example.demo.service.BookService;
import com.example.demo.service.RequestService;
import com.example.demo.service.DashboardService;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.RequestDTO;
import com.example.demo.service.dto.DashboardDTO;
import com.example.demo.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/web/ukr-lit-exchange/request")
@RequiredArgsConstructor
@Slf4j
public class RequestWebController {

    private final BookService bookService;
    private final UserService userService;
    private final DashboardService dashboardService;
    private final RequestService requestService;

    @GetMapping
    public String requestPage(@AuthenticationPrincipal UserDetails userDetails, Model model){
        UserDTO user = userService.getByUsername(userDetails.getUsername());

        model.addAttribute("pendingOrders", requestService.getPendingOrders(user.getId()));
        model.addAttribute("acceptedOrders", requestService.getAcceptedOrders(user.getId()));
        model.addAttribute("sentOrders", requestService.getSentOrders(user.getId()));
        model.addAttribute("completedOrders", requestService.getСompletedOrders(user.getId()));
        model.addAttribute("currentUser", user);

        return "request-page";
    }


    @PostMapping("/accept")
    public String acceptRequest(@RequestParam Long requestId) {
        requestService.acceptRequest(requestId);
        return "redirect:/web/ukr-lit-exchange/request";
    }

    @PostMapping("/decline")
    public String declineRequest(@RequestParam Long requestId) {
        requestService.declineRequest(requestId);
        return "redirect:/web/ukr-lit-exchange/request";
    }

    @PostMapping("/complete")
    public String completeRequest(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long requestId) {
        UserDTO user = userService.getByUsername(userDetails.getUsername());

        requestService.completeRequest(requestId, user.getId());
        return "redirect:/web/ukr-lit-exchange/request";
    }

    @PostMapping("/cancel")
    public String cancelRequest(@RequestParam Long requestId) {
        requestService.deleteById(requestId);
        return "redirect:/web/ukr-lit-exchange/request";
    }

    @GetMapping("/create/exchange-request/{requestBoardId}")
    public String showRequestExchangeForm(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long requestBoardId,
            Model model
    ) {
        DashboardDTO board = dashboardService.getById(requestBoardId);
        UserDTO sender = userService.getByUsername(userDetails.getUsername());

        RequestDTO dto = new RequestDTO();
        dto.setDashboard(board);
        model.addAttribute("requestDTO", dto);
        model.addAttribute("userBooks", bookService.getBooksByOwnerId(sender.getId()));
        return "dashboard-request-exchange";
    }

    @PostMapping("/perform_create/exchange-request")
    public String createExchangeRequest(
            @ModelAttribute("requestDTO") RequestDTO requestDTO,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        DashboardDTO board = dashboardService.getById(requestDTO.getDashboard().getId());
        UserDTO sender = userService.getByUsername(userDetails.getUsername());

        requestDTO.setSender(sender);
        requestDTO.setReceiver(board.getUser());
        requestDTO.setRequestStatus(RequestStatus.PENDING);
        requestDTO.setReceiverBook(board.getSenderBook());

        requestService.save(requestDTO);

        return "redirect:/web/ukr-lit-exchange/request";
    }



    @GetMapping("/create/donation-request/{requestBoardId}")
    public String showRequestDonationForm(
            @PathVariable Long requestBoardId,
            Model model
    ) {
        DashboardDTO requestBoard = dashboardService.getById(requestBoardId);

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setDashboard(requestBoard);

        model.addAttribute("requestDTO", requestDTO);
        return "dashboard-request-donation";
    }

    @PostMapping("/perform_create/donation-request")
    public String createDonationRequest(
            @ModelAttribute("requestDTO") RequestDTO requestDTO,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        DashboardDTO board = dashboardService.getById(requestDTO.getDashboard().getId());
        UserDTO sender = userService.getByUsername(userDetails.getUsername());

        requestDTO.setDashboard(board);
        requestDTO.setSender(sender);
        requestDTO.setReceiver(board.getUser());
        requestDTO.setRequestStatus(RequestStatus.PENDING);
        requestDTO.setReceiverBook(board.getSenderBook());

        requestService.save(requestDTO);

        return "redirect:/web/ukr-lit-exchange/request";
    }



}
