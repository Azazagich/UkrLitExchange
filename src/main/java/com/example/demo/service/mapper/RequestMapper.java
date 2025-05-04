package com.example.demo.service.mapper;

import com.example.demo.domain.Request;
import com.example.demo.service.dto.RequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestMapper extends MapperEntity<Request, RequestDTO>{

    // The great way to map
//    DashboardMapper dashboardMapper = new DashboardMapperImpl();
//
//    @Mapping(target = "dashboard", expression = "java(dashboardMapper.toEntity(dto.getDashboardDTO()))")
//    public abstract Request toEntity(RequestDTO dto);
}
