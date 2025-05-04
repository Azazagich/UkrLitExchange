package com.example.demo.service;

import com.example.demo.domain.Dashboard;
import com.example.demo.domain.User;
import com.example.demo.repository.DashboardRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dto.DashboardDTO;
import com.example.demo.service.mapper.DashboardMapper;
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
public class DashboardService implements CrudService<DashboardDTO, Long> {

    private final UserRepository userRepository;
    private final DashboardMapper dashboardMapper;
    private final DashboardRepository dashboardRepository;

    @Override
    public DashboardDTO getById(Long id) {
        log.debug("Fetching request board with id: {}", id);
        return dashboardRepository.findById(id)
                .map(dashboardMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("RequestBoard not found with id: " + id));
    }

    @Override
    public List<DashboardDTO> getAll() {
        log.debug("Fetching all request boards");
        return dashboardMapper.toDTO(dashboardRepository.findAll());
    }

    @Override
    public DashboardDTO save(DashboardDTO dashboardDTO) {
        log.debug("Saving request board: {}", dashboardDTO);

        if (dashboardDTO == null) {
            throw new IllegalArgumentException("RequestBoardDTO cannot be null");
        }

        var savedRequestBoard = dashboardRepository.save(dashboardMapper.toEntity(dashboardDTO));
        return dashboardMapper.toDTO(savedRequestBoard);
    }

    public Page<DashboardDTO> getRequestBookByOwnerId(Long userId, int page, int limit){
        log.debug("Get books where user id: {}", userId);
        return dashboardRepository.getRequestsByOwnerId(userId, PageRequest.of(page, limit))
                .map(dashboardMapper::toDTO);
    }

    public Page<DashboardDTO> getRequestBooksExceptOwnerId(Long userId, int page, int limit){
        log.debug("Get all books except user id: {}", userId);
        return dashboardRepository.getRequestBooksExceptOwnerId(userId, PageRequest.of(page, limit))
                .map(dashboardMapper::toDTO);
    }

    public void saveDashboardBookByOwnerId(Long userId, DashboardDTO dashboardDTO){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Dashboard dashboard = dashboardMapper.toEntity(dashboardDTO);
        dashboard.setUser(user);
        dashboardRepository.save(dashboard);
    }

    @Override
    public DashboardDTO updateAll(Long id, DashboardDTO dashboardDTO) {
        log.debug("Full updating request board with id: {}", id);

        if (dashboardDTO == null) {
            throw new IllegalArgumentException("RequestBoardDTO cannot be null");
        }

        Dashboard dashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RequestBoard not found with id: " + id));

        dashboardMapper.fullUpdate(dashboardDTO, dashboard);
        Dashboard updatedDashboard = dashboardRepository.save(dashboard);

        return dashboardMapper.toDTO(updatedDashboard);
    }

    @Override
    public DashboardDTO update(Long id, DashboardDTO dashboardDTO) {
        log.debug("Partial updating request board with id: {}", id);

        if (dashboardDTO == null) {
            throw new IllegalArgumentException("RequestBoardDTO cannot be null");
        }

        Dashboard dashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RequestBoard not found with id: " + id));

        dashboardMapper.partialUpdate(dashboardDTO, dashboard);
        Dashboard updatedDashboard = dashboardRepository.save(dashboard);

        return dashboardMapper.toDTO(updatedDashboard);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting request board with id: {}", id);

        if (!dashboardRepository.existsById(id)) {
            throw new RuntimeException("RequestBoard not found with id: " + id);
        }

        dashboardRepository.deleteById(id);
        log.info("Successfully deleted request board with id: {}", id);
    }
}
