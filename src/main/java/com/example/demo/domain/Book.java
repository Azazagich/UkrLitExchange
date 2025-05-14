package com.example.demo.domain;

import com.example.demo.domain.enumeration.BookCondition;
import com.example.demo.domain.enumeration.Genre;
import com.example.demo.domain.enumeration.Language;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "book")
public class Book extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 70)
    private String title;

    @Column(nullable = false, length = 50)
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookCondition condition;

    @Column
    private String description;

    @Column
    private String photoBookUrl;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    private User owner;
}