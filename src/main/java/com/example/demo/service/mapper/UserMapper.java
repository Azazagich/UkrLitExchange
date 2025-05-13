package com.example.demo.service.mapper;

import com.example.demo.domain.Book;
import com.example.demo.domain.User;
import com.example.demo.service.dto.BookDTO;
import com.example.demo.service.dto.UserDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends MapperEntity<User, UserDTO>{

    @Named("toDTOBook")
    @Mapping(target = "owner", ignore = true)
    BookDTO toDTOBook(Book book);


    @Named("toDTO")
    @Mapping(target = "books", qualifiedByName = "toDTOBook")
    UserDTO toDTO(User user);

    @Named("toLazyDTO")
    @Mapping(target = "books", ignore = true)
    UserDTO toLazyDTO(User user);

    @IterableMapping(qualifiedByName = "toLazyDTO")
    List<UserDTO> toDTO(List<User> users);
}
