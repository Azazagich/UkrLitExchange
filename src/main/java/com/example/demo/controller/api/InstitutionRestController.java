package com.example.demo.controller.api;

import com.example.demo.service.InstitutionService;
import com.example.demo.service.dto.InstitutionDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ukr-lit-exchange/institutions")
@RequiredArgsConstructor
@Slf4j
public class InstitutionRestController {

    private final InstitutionService institutionService;

    @GetMapping("/{id}")
    public InstitutionDTO get(@PathVariable Long id){
        return institutionService.getById(id);
    }

    @GetMapping
    public List<InstitutionDTO> getAll(){
        return institutionService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstitutionDTO save(@Valid @RequestBody InstitutionDTO institutionDTO){
        return institutionService.save(institutionDTO);
    }

    @PutMapping("/{id}")
    public InstitutionDTO fullUpdate(@PathVariable Long id, @RequestBody InstitutionDTO institutionDTO){
        institutionService.updateAll(id, institutionDTO);
        return institutionDTO;
    }

    @PatchMapping("/{id}")
    public InstitutionDTO partialUpdate(@PathVariable Long id, @RequestBody InstitutionDTO institutionDTO){
        institutionService.update(id, institutionDTO);
        return institutionDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById (@PathVariable Long id){
        institutionService.deleteById(id);
    }
}
