package com.example.demo.service.dto;

import com.example.demo.domain.Book;
import com.example.demo.domain.enumeration.ExchangeMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO implements Serializable {

    private Long id;

    @NotNull(message = "Book status cannot be empty")
    private ExchangeMethod status;

    private String description;

    private UserDTO user;

    @NotNull(message = "Book cannot be empty")
    private BookDTO senderBook;
}
