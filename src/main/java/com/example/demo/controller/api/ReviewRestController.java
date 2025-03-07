package com.example.demo.controller.api;

import com.example.demo.service.ReviewService;
import com.example.demo.service.dto.ReviewDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ukr-lit-exchange/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewRestController {

    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public ReviewDTO get(@PathVariable Long id){
        return reviewService.getById(id);
    }

    @GetMapping
    public List<ReviewDTO> getAll(){
        return reviewService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDTO save(@Valid @RequestBody ReviewDTO reviewDTO){
        return reviewService.save(reviewDTO);
    }

    @PutMapping("/{id}")
    public ReviewDTO fullUpdate(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO){
        reviewService.updateAll(id, reviewDTO);
        return reviewDTO;
    }

    @PatchMapping("/{id}")
    public ReviewDTO partialUpdate(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO){
        reviewService.update(id, reviewDTO);
        return reviewDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById (@PathVariable Long id){
        reviewService.deleteById(id);
    }
}
