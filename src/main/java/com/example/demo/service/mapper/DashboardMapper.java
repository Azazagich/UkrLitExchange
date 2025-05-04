package com.example.demo.service.mapper;

import com.example.demo.domain.Dashboard;
import com.example.demo.service.dto.DashboardDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DashboardMapper extends MapperEntity<Dashboard, DashboardDTO>{


}
