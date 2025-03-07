package com.example.demo.repository;

import com.example.demo.domain.Book;
import com.example.demo.service.dto.BookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select book from Book book join book.owners owner where owner.id = :id")
    List<Book> getBooksByOwnerId(@Param("id") Long id);

}
