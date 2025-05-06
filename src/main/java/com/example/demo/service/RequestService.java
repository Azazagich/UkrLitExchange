package com.example.demo.service;


import com.example.demo.domain.Request;
import com.example.demo.domain.enumeration.RequestStatus;
import com.example.demo.repository.RequestRepository;
import com.example.demo.service.dto.RequestDTO;
import com.example.demo.service.mapper.RequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RequestService implements CrudService<RequestDTO, Long>{

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

//
//    public List<OrderDTO> getAllOrdersByUser(Long userId) {
//        return orderRepository.findAllOrdersBySenderId(userId)
//                .stream()
//                .map(orderMapper::toDTO)
//                .collect(Collectors.toList());
//    }


    public void acceptRequest(Long requestId) {
        RequestDTO request = getById(requestId);
        request.setRequestStatus(RequestStatus.ACCEPTED);
        update(requestId, request);
    }

    public void declineRequest(Long requestId) {
        RequestDTO request = getById(requestId);
        request.setRequestStatus(RequestStatus.DECLINE);
        update(requestId, request);
    }

    public void completeRequest(Long requestId, Long currentUserId) {
        RequestDTO request = getById(requestId);

        if (request.getSender().getId().equals(currentUserId)) {
            request.setSenderCompleted(true);
            request.setRequestStatus(RequestStatus.PRE_COMPLETED);
        } else if (request.getReceiver().getId().equals(currentUserId)) {
            request.setReceiverCompleted(true);
            request.setRequestStatus(RequestStatus.PRE_COMPLETED);
        }

        if (Boolean.TRUE.equals(request.getSenderCompleted()) && Boolean.TRUE.equals(request.getReceiverCompleted())) {
            request.setRequestStatus(RequestStatus.COMPLETED);
        }

        update(requestId, request);
    }

    public List<RequestDTO> getСompletedOrders(Long userId) {
        return requestRepository.findByRequestStatusAndUserInvolved(RequestStatus.PRE_COMPLETED, userId)
                .stream()
                .map(requestMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<RequestDTO> getPendingOrders(Long userId) {
        return requestRepository.findByRequestStatusAndUserInvolved(RequestStatus.PENDING, userId)
                .stream()
                .map(requestMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<RequestDTO> getAcceptedOrders(Long userId) {
        return requestRepository.findByRequestStatusAndUserInvolved(RequestStatus.ACCEPTED, userId)
                .stream()
                .map(requestMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<RequestDTO> getSentOrders(Long userId) {
        return requestRepository.findAllOrdersBySenderId(userId)
                .stream()
                .map(requestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RequestDTO getById(Long id) {
        log.debug("Fetching order with id: {}", id);
        return requestRepository.findById(id)
                .map(requestMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    public List<RequestDTO> getAll() {
        log.debug("Fetching all orders");
        return requestMapper.toDTO(requestRepository.findAll());
    }

    @Override
    public RequestDTO save(RequestDTO requestDTO) {
        log.debug("Saving order: {}", requestDTO);

        if (requestDTO == null) {
            throw new IllegalArgumentException("OrderDTO cannot be null");
        }
        Request request = requestMapper.toEntity(requestDTO);
        Request savedRequest = requestRepository.save(request);
        return requestMapper.toDTO(savedRequest);
    }

    @Override
    public RequestDTO updateAll(Long id, RequestDTO requestDTO) {
        log.debug("Full updating order with id: {}", id);

        if (requestDTO == null) {
            throw new IllegalArgumentException("OrderDTO cannot be null");
        }

        Request existingRequest = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        requestMapper.fullUpdate(requestDTO, existingRequest);
        Request updatedRequest = requestRepository.save(existingRequest);

        return requestMapper.toDTO(updatedRequest);
    }

    @Override
    public RequestDTO update(Long id, RequestDTO requestDTO) {
        log.debug("Partial updating order with id: {}", id);

        if (requestDTO == null) {
            throw new IllegalArgumentException("OrderDTO cannot be null");
        }

        Request existingRequest = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        requestMapper.partialUpdate(requestDTO, existingRequest);
        Request updatedRequest = requestRepository.save(existingRequest);

        return requestMapper.toDTO(updatedRequest);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting order with id: {}", id);

        if (!requestRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }

        requestRepository.deleteById(id);
        log.info("Successfully deleted order with id: {}", id);
    }

}
