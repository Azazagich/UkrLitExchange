package com.example.demo.service;


import com.example.demo.domain.Book;
import com.example.demo.domain.Institution;
import com.example.demo.repository.InstitutionRepository;
import com.example.demo.service.dto.InstitutionDTO;
import com.example.demo.service.mapper.GroupMapper;
import com.example.demo.service.mapper.InstitutionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class InstitutionService implements CrudService<InstitutionDTO, Long>{

    private final InstitutionMapper institutionMapper;
    private final InstitutionRepository institutionRepository;


    @Override
    public InstitutionDTO getById(Long id) {
        log.debug("Fetching institution with id: {}", id);
        return institutionRepository.findById(id)
                .map(institutionMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Institution not found with id: " + id));
    }

    @Override
    public List<InstitutionDTO> getAll() {
        log.debug("Fetching all institutions");
        return institutionMapper.toDTO(institutionRepository.findAll());
    }

    @Override
    public InstitutionDTO save(InstitutionDTO institutionDTO) {
        log.debug("Saving institution: {}", institutionDTO);

        if (institutionDTO == null) {
            throw new IllegalArgumentException("InstitutionDTO cannot be null");
        }

        var savedInstitution = institutionRepository.save(institutionMapper.toEntity(institutionDTO));
        return institutionMapper.toDTO(savedInstitution);
    }

    @Override
    public InstitutionDTO updateAll(Long id, InstitutionDTO institutionDTO) {
        log.debug("Full updating institution with id: {}", id);

        if (institutionDTO == null) {
            throw new IllegalArgumentException("InstitutionDTO cannot be null");
        }

        Institution institution = institutionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Institution not found with id: " + id));

        institutionMapper.fullUpdate(institutionDTO, institution);
        Institution updatedInstitution = institutionRepository.save(institution);

        return institutionMapper.toDTO(updatedInstitution);
    }

    @Override
    public InstitutionDTO update(Long id, InstitutionDTO institutionDTO) {
        log.debug("Partial updating institution with id: {}", id);

        if (institutionDTO == null) {
            throw new IllegalArgumentException("InstitutionDTO cannot be null");
        }

        Institution institution = institutionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Institution not found with id: " + id));

        institutionMapper.partialUpdate(institutionDTO, institution);
        Institution updatedInstitution = institutionRepository.save(institution);

        return institutionMapper.toDTO(updatedInstitution);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting institution with id: {}", id);

        if (!institutionRepository.existsById(id)) {
            throw new RuntimeException("Institution not found with id: " + id);
        }

        institutionRepository.deleteById(id);
        log.info("Successfully deleted institution with id: {}", id);
    }
}
