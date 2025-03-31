package com.example.demo.service.mapper;

import com.example.demo.domain.User;
import com.example.demo.service.dto.UserRegistrationDTO;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface RegistrationMapper extends MapperEntity<User, UserRegistrationDTO>{

    @Named("toRegisteredUser")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "fullName", source = "fullName")
    User toEntity(UserRegistrationDTO registrationDTO);

}
