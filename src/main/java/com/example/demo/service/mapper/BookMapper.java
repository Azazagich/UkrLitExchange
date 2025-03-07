package com.example.demo.service.mapper;


import com.example.demo.domain.Book;
import com.example.demo.domain.User;
import com.example.demo.service.dto.BookDTO;
import com.example.demo.service.dto.UserDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper extends MapperEntity<Book, BookDTO>{

    @Named("toEagerBookDTO")
    @Mapping(target = "owners", qualifiedByName = "toDTOOwner")
    BookDTO toDTO(Book book);

    @Named("toDTOOwner")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "location", source = "location")
    UserDTO toOwnerDTO(User user);

    @Named("toLazyDTOBook")
    @Mapping(target = "owners", ignore = true)
    BookDTO toLazyDTO(Book book);

    @IterableMapping(qualifiedByName = "toLazyDTOBook")
    List<BookDTO> toDTO(List<Book> book);
}
