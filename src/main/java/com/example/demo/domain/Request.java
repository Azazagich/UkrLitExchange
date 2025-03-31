package com.example.demo.domain;

import com.example.demo.domain.enumeration.RequestStatus;
import jakarta.persistence.*;
import lombok.*;


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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "sender_book_id", nullable = false)
    private Book senderBook;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "receiver_book_id", nullable = false)
    private Book receiverBook;
}