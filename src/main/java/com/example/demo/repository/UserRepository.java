package com.example.demo.repository;

import com.example.demo.domain.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsUsersByEmail(@Email String email);

    boolean existsUserByUsername(String username);
}
