package com.example.demo.service.mapper;

import com.example.demo.domain.Book;
import com.example.demo.domain.Request;
import com.example.demo.domain.User;
import com.example.demo.service.dto.BookDTO;
import com.example.demo.service.dto.RequestDTO;
import com.example.demo.service.dto.UserDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestMapper extends MapperEntity<Request, RequestDTO> {

    @Named("toUserDTO")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "fullName", source = "fullName")
    UserDTO toUserDTO(User user);

    @Named("toBookDTO")
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "owner", ignore = true)
    BookDTO toBookDTO(Book book);

    @Named("toEagerRequestDTO")
    @Mapping(target = "sender", qualifiedByName = "toUserDTO")
    @Mapping(target = "receiver", qualifiedByName = "toUserDTO")
    @Mapping(target = "senderBook", qualifiedByName = "toBookDTO")
    @Mapping(target = "receiverBook", qualifiedByName = "toBookDTO")
    RequestDTO toDTO(Request request);

    @IterableMapping(qualifiedByName = "toEagerRequestDTO")
    List<RequestDTO> toDTO(List<Request> requests);
}
