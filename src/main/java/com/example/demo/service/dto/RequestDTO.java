package com.example.demo.service.dto;

import com.example.demo.domain.enumeration.DeliveryMethod;
import com.example.demo.domain.enumeration.RequestStatus;
import jakarta.persistence.Column;
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

    private String message;

    private BookDTO senderBook;

    private BookDTO receiverBook;

    private UserDTO receiver;

    private UserDTO sender;

    private Boolean receiverCompleted;

    private Boolean senderCompleted;

    private DeliveryMethod deliveryMethod;

    private RequestStatus requestStatus;

    private DashboardDTO dashboard;
}
