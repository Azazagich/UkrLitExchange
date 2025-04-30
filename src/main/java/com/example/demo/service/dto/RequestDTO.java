package com.example.demo.service.dto;

import com.example.demo.domain.enumeration.RequestStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO implements Serializable {

    private Long id;

    @NotNull(message = "Request status cannot be null")
    private RequestStatus status;

    private UserDTO sender;

    private UserDTO receiver;

    private BookDTO senderBook;

    private BookDTO receiverBook;
}
