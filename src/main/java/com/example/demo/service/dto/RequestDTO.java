package com.example.demo.service.dto;

import com.example.demo.domain.Book;
import com.example.demo.domain.User;
import com.example.demo.domain.enumeration.RequestStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO implements Serializable {

    private Long id;

    @NotNull(message = "Request status cannot be null")
    private RequestStatus status;

    @Positive(message = "Price must be positive")
    private double price;

    private UserDTO sender;

    private UserDTO receiver;

    private BookDTO senderBook;

    private BookDTO receiverBook;
}
