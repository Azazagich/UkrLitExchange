package com.example.demo.controller.api;

import com.example.demo.service.UserService;
import com.example.demo.service.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ukr-lit-exchange/users")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable Long id){
        return userService.getById(id);
    }

    @GetMapping
    public List<UserDTO> getAll(){
        return userService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO save(@Valid @RequestBody UserDTO userDTO){
        return userService.save(userDTO);
    }

    @PutMapping("/{id}")
    public UserDTO fullUpdate(@PathVariable Long id, @RequestBody UserDTO userDTO){
        userService.updateAll(id, userDTO);
        return userDTO;
    }

    @PatchMapping("/{id}")
    public UserDTO partialUpdate(@PathVariable Long id, @RequestBody UserDTO userDTO){
        userService.update(id, userDTO);
        return userDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById (@PathVariable Long id){
        userService.deleteById(id);
    }
}
