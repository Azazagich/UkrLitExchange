package com.example.demo.repository;

import com.example.demo.domain.Dashboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long> {

    @Query("select request from Dashboard request join request.user owner where owner.id = :id")
    Page<Dashboard> getRequestsByOwnerId(@Param("id") Long id, Pageable pageable);

    @Query("select request from Dashboard request join request.user owner where owner.id <> :id")
    Page<Dashboard> getRequestBooksExceptOwnerId(@Param("id") Long id, Pageable pageable);
}
