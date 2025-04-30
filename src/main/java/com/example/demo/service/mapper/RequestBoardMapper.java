package com.example.demo.service.mapper;

import com.example.demo.domain.RequestBoard;
import com.example.demo.service.dto.RequestBoardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RequestBoardMapper extends MapperEntity<RequestBoard, RequestBoardDTO>{ }
