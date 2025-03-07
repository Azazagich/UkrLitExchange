
package com.example.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @Size(min = 8)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String location;

    @Column
    private String bio;

    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "users_books",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "participants")
    private Set<Group> groups;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;

    //TODO
    //    @EqualsAndHashCode.Exclude
    //    @OneToMany(mappedBy = "reviewer")
    //    private Set<Review> givenReviews;
}
