package com.example.demo.service;

import com.example.demo.domain.RequestBoard;
import com.example.demo.domain.User;
import com.example.demo.repository.RequestBoardRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dto.RequestBoardDTO;
import com.example.demo.service.mapper.RequestBoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RequestBoardService implements CrudService<RequestBoardDTO, Long> {

    private final UserRepository userRepository;
    private final RequestBoardMapper requestBoardMapper;
    private final RequestBoardRepository requestBoardRepository;

    @Override
    public RequestBoardDTO getById(Long id) {
        log.debug("Fetching request board with id: {}", id);
        return requestBoardRepository.findById(id)
                .map(requestBoardMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("RequestBoard not found with id: " + id));
    }

    @Override
    public List<RequestBoardDTO> getAll() {
        log.debug("Fetching all request boards");
        return requestBoardMapper.toDTO(requestBoardRepository.findAll());
    }

    @Override
    public RequestBoardDTO save(RequestBoardDTO requestBoardDTO) {
        log.debug("Saving request board: {}", requestBoardDTO);

        if (requestBoardDTO == null) {
            throw new IllegalArgumentException("RequestBoardDTO cannot be null");
        }

        var savedRequestBoard = requestBoardRepository.save(requestBoardMapper.toEntity(requestBoardDTO));
        return requestBoardMapper.toDTO(savedRequestBoard);
    }

    public Page<RequestBoardDTO> getRequestBookByOwnerId(Long userId, int page, int limit){
        log.debug("Get books where user id: {}", userId);
        return requestBoardRepository.getRequestsByOwnerId(userId, PageRequest.of(page, limit))
                .map(requestBoardMapper::toDTO);
    }

    public Page<RequestBoardDTO> getRequestBooksExceptOwnerId(Long userId, int page, int limit){
        log.debug("Get all books except user id: {}", userId);
        return requestBoardRepository.getRequestBooksExceptOwnerId(userId, PageRequest.of(page, limit))
                .map(requestBoardMapper::toDTO);
    }

    public void saveDashboardBookByOwnerId(Long userId, RequestBoardDTO requestBoardDTO){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RequestBoard requestBoard = requestBoardMapper.toEntity(requestBoardDTO);
        requestBoard.setUser(user);
        requestBoardRepository.save(requestBoard);
    }

    @Override
    public RequestBoardDTO updateAll(Long id, RequestBoardDTO requestBoardDTO) {
        log.debug("Full updating request board with id: {}", id);

        if (requestBoardDTO == null) {
            throw new IllegalArgumentException("RequestBoardDTO cannot be null");
        }

        RequestBoard requestBoard = requestBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RequestBoard not found with id: " + id));

        requestBoardMapper.fullUpdate(requestBoardDTO, requestBoard);
        RequestBoard updatedRequestBoard = requestBoardRepository.save(requestBoard);

        return requestBoardMapper.toDTO(updatedRequestBoard);
    }

    @Override
    public RequestBoardDTO update(Long id, RequestBoardDTO requestBoardDTO) {
        log.debug("Partial updating request board with id: {}", id);

        if (requestBoardDTO == null) {
            throw new IllegalArgumentException("RequestBoardDTO cannot be null");
        }

        RequestBoard requestBoard = requestBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RequestBoard not found with id: " + id));

        requestBoardMapper.partialUpdate(requestBoardDTO, requestBoard);
        RequestBoard updatedRequestBoard = requestBoardRepository.save(requestBoard);

        return requestBoardMapper.toDTO(updatedRequestBoard);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting request board with id: {}", id);

        if (!requestBoardRepository.existsById(id)) {
            throw new RuntimeException("RequestBoard not found with id: " + id);
        }

        requestBoardRepository.deleteById(id);
        log.info("Successfully deleted request board with id: {}", id);
    }
}
