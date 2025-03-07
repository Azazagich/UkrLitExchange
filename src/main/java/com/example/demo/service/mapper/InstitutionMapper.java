package com.example.demo.service.mapper;

import com.example.demo.domain.Institution;
import com.example.demo.domain.User;
import com.example.demo.service.dto.InstitutionDTO;
import com.example.demo.service.dto.UserDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InstitutionMapper extends MapperEntity<Institution, InstitutionDTO>{

    @Named("toEagerInstitutionDTO")
    @Mapping(target = "participants", qualifiedByName = "toDTOParticipant")
    InstitutionDTO toDTO(Institution institution);

    @Named("toDTOParticipant")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "fullName", source = "fullName")
    UserDTO toParticipantDTO(User user);

    @IterableMapping(qualifiedByName = "toEagerInstitutionDTO")
    List<InstitutionDTO> toDTO(List<Institution> institutions);
}
