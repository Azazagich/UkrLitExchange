package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.domain.enumeration.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dto.UserRegistrationDTO;
import com.example.demo.service.mapper.RegistrationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;

    private final PasswordEncoder bCryptPasswordEncoder;

    private final RegistrationMapper registrationMapper;

    public void registerUser(UserRegistrationDTO userRegistrationDTO){
        if (userRepository.existsUsersByEmail(userRegistrationDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsUserByUsername(userRegistrationDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        User user = registrationMapper.toEntity(userRegistrationDTO);
        user.setPassword(bCryptPasswordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }
}

