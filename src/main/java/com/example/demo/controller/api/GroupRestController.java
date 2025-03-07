package com.example.demo.controller.api;

import com.example.demo.service.GroupService;
import com.example.demo.service.dto.GroupDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ukr-lit-exchange/groups")
@RequiredArgsConstructor
@Slf4j
public class GroupRestController {

    private final GroupService groupService;

    @GetMapping("/{id}")
    public GroupDTO get(@PathVariable Long id){
        return groupService.getById(id);
    }

    @GetMapping
    public List<GroupDTO> getAll(){
        return groupService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDTO save(@Valid @RequestBody GroupDTO groupDTO){
        return groupService.save(groupDTO);
    }

    @PutMapping("/{id}")
    public GroupDTO fullUpdate(@PathVariable Long id, @RequestBody GroupDTO groupDTO){
        groupService.updateAll(id, groupDTO);
        return groupDTO;
    }

    @PatchMapping("/{id}")
    public GroupDTO partialUpdate(@PathVariable Long id, @RequestBody GroupDTO groupDTO){
        groupService.update(id, groupDTO);
        return groupDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById (@PathVariable Long id){
        groupService.deleteById(id);
    }
}
