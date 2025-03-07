package com.example.demo.service.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

public interface MapperEntity<Entity, DTO> {

    DTO toDTO(Entity entity);

    Entity toEntity(DTO dto);

    List<DTO> toDTO(List<Entity> entities);

    List<Entity> toEntity(List<DTO> dtos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(DTO dto, @MappingTarget Entity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    void fullUpdate(DTO dto, @MappingTarget Entity entity);
}
