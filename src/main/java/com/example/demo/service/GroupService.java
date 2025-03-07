package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.domain.Group;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.GroupRepository;
import com.example.demo.service.dto.GroupDTO;
import com.example.demo.service.mapper.GroupMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class GroupService implements CrudService<GroupDTO, Long>{

    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;

    @Override
    public GroupDTO getById(Long id) {
        log.debug("Fetching group with id: {}", id);
        return groupRepository.findById(id)
                .map(groupMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
    }

    @Override
    public List<GroupDTO> getAll() {
        log.debug("Fetching all groups");
        return groupMapper.toDTO(groupRepository.findAll());
    }

    @Override
    public GroupDTO save(GroupDTO groupDTO) {
        log.debug("Saving group: {}", groupDTO);

        if (groupDTO == null) {
            throw new IllegalArgumentException("GroupDTO cannot be null");
        }

        var savedGroup = groupRepository.save(groupMapper.toEntity(groupDTO));
        return groupMapper.toDTO(savedGroup);
    }

    @Override
    public GroupDTO updateAll(Long id, GroupDTO groupDTO) {
        log.debug("Full updating group with id: {}", id);

        if (groupDTO == null) {
            throw new IllegalArgumentException("GroupDTO cannot be null");
        }

        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));

        groupMapper.fullUpdate(groupDTO, group);
        Group updatedGroup = groupRepository.save(group);

        return groupMapper.toDTO(updatedGroup);
    }

    @Override
    public GroupDTO update(Long id, GroupDTO groupDTO) {
        log.debug("Partial updating group with id: {}", id);

        if (groupDTO == null) {
            throw new IllegalArgumentException("GroupDTO cannot be null");
        }

        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));

        groupMapper.partialUpdate(groupDTO, group);
        Group updatedGroup = groupRepository.save(group);

        return groupMapper.toDTO(updatedGroup);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting group with id: {}", id);

        if (!groupRepository.existsById(id)) {
            throw new RuntimeException("Group not found with id: " + id);
        }

        groupRepository.deleteById(id);
        log.info("Successfully deleted group with id: {}", id);
    }

}
