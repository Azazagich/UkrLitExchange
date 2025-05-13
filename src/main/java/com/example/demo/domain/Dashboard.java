package com.example.demo.domain;

import com.example.demo.domain.enumeration.ExchangeMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table
public class Dashboard extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExchangeMethod status;

    @Column
    private String description;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User user;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "sender_book_id", nullable = false)
    private Book senderBook;
}