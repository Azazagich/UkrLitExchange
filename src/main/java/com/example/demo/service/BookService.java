package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.domain.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dto.BookDTO;
import com.example.demo.service.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BookService implements CrudService<BookDTO, Long>{

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;


//    public List<BookDTO> getBooksByOwnerId(Long id){
//        log.debug("Get books where user id: {}", id);
//        return bookMapper.toDTO(bookRepository.getBooksByOwnerId(id));
//    }

    @Override
    public BookDTO getById(Long id) {
        log.debug("Fetching book with id: {}", id);
        return bookRepository.findById(id)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }


    public Page<BookDTO> getBooksByOwnerId(Long id, int page, int limit){
        log.debug("Get books where user id: {}", id);
        return bookRepository.getBooksByOwnerId(id, PageRequest.of(page, limit,
                        Sort.by(Sort.Order.desc("createdDate"))))
                        .map(bookMapper::toDTO);
    }


    public void saveBookByOwnerId(Long userId, BookDTO bookDTO){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookMapper.toEntity(bookDTO);
        book.setOwner(user);
        bookRepository.save(book);
    }


    @Override
    public List<BookDTO> getAll() {
        log.debug("Fetching all books");
        return bookMapper.toDTO(bookRepository.findAll());
    }


    @Override
    public BookDTO save(BookDTO bookDTO) {
        log.debug("Saving book: {}", bookDTO);

        if (bookDTO == null){
            throw new IllegalArgumentException("BookDTO cannot be null");
        }

        var savedBook = bookRepository.save(bookMapper.toEntity(bookDTO));
        return bookMapper.toDTO(savedBook);
    }


    @Override
    public BookDTO updateAll(Long id, BookDTO bookDTO) {
        log.debug("Full updating book with id: {}", id);

        if (bookDTO == null) {
            throw new IllegalArgumentException("BookDTO cannot be null");
        }

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        bookMapper.fullUpdate(bookDTO, book);
        Book updatedBook = bookRepository.save(book);

        return bookMapper.toDTO(updatedBook);
    }


    @Override
    public BookDTO update(Long id, BookDTO bookDTO) {
        log.debug("Partial updating book with id: {}", id);

        if (bookDTO == null) {
            throw new IllegalArgumentException("BookDTO cannot be null");
        }

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        bookMapper.partialUpdate(bookDTO, book);
        Book updatedBook = bookRepository.save(book);

        return bookMapper.toDTO(updatedBook);
    }


    @Override
    public void deleteById(Long id) {
        log.debug("Deleting book with id: {}", id);

        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }

        bookRepository.deleteById(id);
        log.info("Successfully deleted book with id: {}", id);
    }
}
