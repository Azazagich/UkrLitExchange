package com.example.demo.service;

import com.example.demo.domain.Request;
import com.example.demo.repository.RequestRepository;
import com.example.demo.service.dto.RequestDTO;
import com.example.demo.service.mapper.RequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RequestService implements CrudService<RequestDTO, Long>{

    private final RequestMapper requestMapper;
    private final RequestRepository requestRepository;


    @Override
    public RequestDTO getById(Long id) {
        log.debug("Fetching request with id: {}", id);
        return requestRepository.findById(id)
                .map(requestMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + id));
    }

    @Override
    public List<RequestDTO> getAll() {
        log.debug("Fetching all requests");
        return requestMapper.toDTO(requestRepository.findAll());
    }

    @Override
    public RequestDTO save(RequestDTO requestDTO) {
        log.debug("Saving request: {}", requestDTO);

        if (requestDTO == null) {
            throw new IllegalArgumentException("RequestDTO cannot be null");
        }

        var savedRequest = requestRepository.save(requestMapper.toEntity(requestDTO));
        return requestMapper.toDTO(savedRequest);
    }

    @Override
    public RequestDTO updateAll(Long id, RequestDTO requestDTO) {
        log.debug("Full updating request with id: {}", id);

        if (requestDTO == null) {
            throw new IllegalArgumentException("RequestDTO cannot be null");
        }

        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + id));

        requestMapper.fullUpdate(requestDTO, request);
        Request updatedRequest = requestRepository.save(request);

        return requestMapper.toDTO(updatedRequest);
    }

    @Override
    public RequestDTO update(Long id, RequestDTO requestDTO) {
        log.debug("Partial updating request with id: {}", id);

        if (requestDTO == null) {
            throw new IllegalArgumentException("RequestDTO cannot be null");
        }

        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + id));

        requestMapper.partialUpdate(requestDTO, request);
        Request updatedRequest = requestRepository.save(request);

        return requestMapper.toDTO(updatedRequest);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting request with id: {}", id);

        if (!requestRepository.existsById(id)) {
            throw new RuntimeException("Request not found with id: " + id);
        }

        requestRepository.deleteById(id);
        log.info("Successfully deleted request with id: {}", id);
    }
}
