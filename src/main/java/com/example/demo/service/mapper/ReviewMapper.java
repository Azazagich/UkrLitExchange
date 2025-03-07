package com.example.demo.service.mapper;

import com.example.demo.domain.Review;
import com.example.demo.domain.User;
import com.example.demo.service.dto.ReviewDTO;
import com.example.demo.service.dto.UserDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper extends MapperEntity<Review, ReviewDTO>{

    @Named("toDTOUser")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "fullName", source = "fullName")
    UserDTO toUserDTO(User user);

    @Named("toEagerReviewDTO")
    @Mapping(target = "user", qualifiedByName = "toDTOUser")
    @Mapping(target = "reviewer", qualifiedByName = "toDTOUser")
    ReviewDTO toDTO(Review review);

    @IterableMapping(qualifiedByName = "toEagerReviewDTO")
    List<ReviewDTO> toDTO(List<Review> review);
}
