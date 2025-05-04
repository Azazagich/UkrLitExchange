package com.example.demo.domain;

import com.example.demo.domain.enumeration.DeliveryMethod;
import com.example.demo.domain.enumeration.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String message;

    @Enumerated(EnumType.STRING)
    @Column
    private RequestStatus requestStatus;

    @Enumerated(EnumType.STRING)
    @Column
    private DeliveryMethod deliveryMethod;

    @ManyToOne
    private Book senderBook;  // Книга яку відсилають при запиті на обмін

    @ManyToOne
    private Book receiverBook; // Книга в дашборд

    @ManyToOne
    private User receiver; // Користувач який виставив оголошення в дашборд та отримує запити

    @ManyToOne
    private User sender; // Користувач який надсилає запит

    @ManyToOne
    private Dashboard dashboard;
}
