package com.example.demo.service.mapper;

import com.example.demo.domain.Group;
import com.example.demo.domain.User;
import com.example.demo.service.dto.GroupDTO;
import com.example.demo.service.dto.UserDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper extends MapperEntity<Group, GroupDTO> {

    @Named("toEagerGroupDTO")
    @Mapping(target = "participants", qualifiedByName = "toDTOUser")
    GroupDTO toDTO(Group group);

    @Named("toDTOUser")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "fullName", source = "fullName")
    UserDTO toUserDTO(User user);

    @IterableMapping(qualifiedByName = "toEagerGroupDTO")
    List<GroupDTO> toDTO(List<Group> groups);
}
