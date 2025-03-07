package com.example.demo.controller.api;

import com.example.demo.service.RequestService;
import com.example.demo.service.dto.RequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ukr-lit-exchange/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestRestController {

    private final RequestService requestService;

    @GetMapping("/{id}")
    public RequestDTO get(@PathVariable Long id){
        return requestService.getById(id);
    }

    @GetMapping
    public List<RequestDTO> getAll(){
        return requestService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDTO save(@Valid @RequestBody RequestDTO requestDTO){
        return requestService.save(requestDTO);
    }

    @PutMapping("/{id}")
    public RequestDTO fullUpdate(@PathVariable Long id, @RequestBody RequestDTO requestDTO){
        requestService.updateAll(id, requestDTO);
        return requestDTO;
    }

    @PatchMapping("/{id}")
    public RequestDTO partialUpdate(@PathVariable Long id, @RequestBody RequestDTO requestDTO){
        requestService.update(id, requestDTO);
        return requestDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById (@PathVariable Long id){
        requestService.deleteById(id);
    }
}
