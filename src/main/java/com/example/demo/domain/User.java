package com.example.demo.domain;

import com.example.demo.domain.enumeration.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    @Size(min = 8)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Column
    private String location;

    @Column
    private String bio;

    @Column
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @EqualsAndHashCode.Exclude
    @OneToMany
    @JoinColumn(name = "book_id")
    private Set<Book> books;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "participants")
    private Set<Group> groups;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "reviewer")
    private Set<Review> givenReviews;

    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "user_friends",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(getRole().getAuthority());
        return Collections.singletonList(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}