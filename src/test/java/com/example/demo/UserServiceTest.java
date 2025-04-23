package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.UserDTO;
import com.example.demo.service.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/** Module testing for {@link com.example.demo.service.UserService}*/
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUserByUsername(){
        User user = User.builder()
                .id(1L)
                .username("Azazagich")
                .email("azazagich@gmail.com")
                .password("_admin_")
                .build();

        when(userRepository.findByUsername("Azazagich")).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(UserDTO.builder().username("Azazagich").email("azazagich@gmail.com").build());

        UserDTO result = userService.getByUsername("Azazagich");

        assertAll(
                () -> assertEquals(user.getUsername(), result.getUsername()),
                () -> assertEquals(user.getEmail(), result.getEmail())
        );

    }
}
