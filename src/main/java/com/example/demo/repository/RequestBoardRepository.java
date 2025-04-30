package com.example.demo.repository;

import com.example.demo.domain.RequestBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestBoardRepository extends JpaRepository<RequestBoard, Long> {

    @Query("select request from RequestBoard request join request.user owner where owner.id = :id")
    Page<RequestBoard> getRequestsByOwnerId(@Param("id") Long id, Pageable pageable);

    @Query("select request from RequestBoard request join request.user owner where owner.id <> :id")
    Page<RequestBoard> getRequestBooksExceptOwnerId(@Param("id") Long id, Pageable pageable);
}
