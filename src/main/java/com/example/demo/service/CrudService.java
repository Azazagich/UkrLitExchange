package com.example.demo.service;

import java.util.List;

public interface CrudService <DTO, ID extends Long>{

    DTO getById(ID id);

    List<DTO> getAll();

    DTO save(DTO dto);

    DTO updateAll(ID id, DTO dto);

    DTO update(ID id, DTO dto);

    void deleteById(ID id);
}
