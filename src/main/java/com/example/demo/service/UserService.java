package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dto.UserDTO;
import com.example.demo.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService, CrudService<UserDTO, Long>{

    private final UserRepository userRepository;
    private final UserMapper userMapper;



    @Override
    public UserDTO getById(Long id) {
        log.debug("Fetching user with id: {}", id);
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public List<UserDTO> getAll() {
        log.debug("Fetching all users");
        return userMapper.toDTO(userRepository.findAll());
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        log.debug("Saving user: {}", userDTO);

        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }

        var savedUser = userRepository.save(userMapper.toEntity(userDTO));
        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO updateAll(Long id, UserDTO userDTO) {
        log.debug("Full updating user with id: {}", id);

        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        userMapper.fullUpdate(userDTO, user);
        User updatedUser = userRepository.save(user);

        return userMapper.toDTO(updatedUser);
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) {
        log.debug("Partial updating user with id: {}", id);

        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        userMapper.partialUpdate(userDTO, user);
        User updatedUser = userRepository.save(user);

        return userMapper.toDTO(updatedUser);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting user with id: {}", id);

        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
        log.info("Successfully deleted user with id: {}", id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found: " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
}
