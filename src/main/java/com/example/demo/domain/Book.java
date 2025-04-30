package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "book")
public class Book extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 70)
    private String title;

    @Column(nullable = false, length = 50)
    private String author;

    @Column(nullable = false, length = 50)
    private String genre;

    @Column(nullable = false, length = 50)
    private String language;

//  TODO do (enum) for this field
    @Column(nullable = false, length = 30)
    private String condition;

    @Column
    private String description;

    @Column
    private String photoBookUrl;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    private User owner;
}