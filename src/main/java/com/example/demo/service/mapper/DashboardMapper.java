package com.example.demo.service.mapper;

import com.example.demo.domain.Dashboard;
import com.example.demo.domain.User;
import com.example.demo.service.dto.DashboardDTO;
import com.example.demo.service.dto.UserRegistrationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DashboardMapper extends MapperEntity<Dashboard, DashboardDTO>{

    @Mapping(target = "deleted", source = "deleted")
    Dashboard toEntity(DashboardDTO dashboardDTO);
}
