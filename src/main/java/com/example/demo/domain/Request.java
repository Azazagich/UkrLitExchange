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

    @Column
    private Boolean receiverCompleted;

    @Column
    private Boolean senderCompleted;

    @OneToOne
    private Book senderBook;

    @OneToOne
    private Book receiverBook;

    @ManyToOne
    private User receiver;

    @ManyToOne
    private User sender;

    @ManyToOne
    private Dashboard dashboard;
}
