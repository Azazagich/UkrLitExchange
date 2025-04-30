package com.example.demo.service.dto;

import com.example.demo.domain.Book;
import com.example.demo.domain.User;
import com.example.demo.domain.enumeration.ExchangeMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestBoardDTO {

    private Long id;

    @NotNull(message = "Book status cannot be empty")
    private ExchangeMethod status;

    private String description;

    private User user;

    @NotNull(message = "Book cannot be empty")
    private Book senderBook;
}
