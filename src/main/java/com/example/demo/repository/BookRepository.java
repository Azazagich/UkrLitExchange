package com.example.demo.repository;

import com.example.demo.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select book from Book book join book.owner owner where owner.id = :id")
    List<Book> getBooksByOwnerId(@Param("id") Long userId);

    @Query("select book from Book book join book.owner owner where owner.id = :id")
    Page<Book> getBooksByOwnerId(@Param("id") Long id, Pageable pageable);

    @Query("select book from Book book " +
            "join book.owner owner where owner.id = :id and " +
            "(lower(book.title) like lower(concat(:input, '%')) or " +
            "lower(book.author) like lower(concat(:input, '%')))"
    )
    Page<Book> findByTitleContainingIgnoreCase (@Param("id") Long ownerId, @Param("input") String input, Pageable pageable);
}
