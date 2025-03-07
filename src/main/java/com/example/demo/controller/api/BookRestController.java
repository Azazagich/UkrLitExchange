package com.example.demo.controller.api;

import com.example.demo.service.BookService;
import com.example.demo.service.dto.BookDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ukr-lit-exchange/books")
@RequiredArgsConstructor
@Slf4j
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public BookDTO get(@PathVariable Long id){
        return bookService.getById(id);
    }

    @GetMapping
    public List<BookDTO> getAll(){
        return bookService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO save(@Valid @RequestBody BookDTO bookDTO){
        return bookService.save(bookDTO);
    }

    @PutMapping("/{id}")
    public BookDTO fullUpdate(@PathVariable Long id, @RequestBody BookDTO bookDTO){
        bookService.updateAll(id, bookDTO);
        return bookDTO;
    }

    @PatchMapping("/{id}")
    public BookDTO partialUpdate(@PathVariable Long id, @RequestBody BookDTO bookDTO){
         bookService.update(id, bookDTO);
         return bookDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById (@PathVariable Long id){
        bookService.deleteById(id);
    }
}
