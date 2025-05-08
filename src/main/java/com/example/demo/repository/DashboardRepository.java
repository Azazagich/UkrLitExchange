package com.example.demo.repository;

import com.example.demo.domain.Dashboard;
import com.example.demo.domain.enumeration.ExchangeMethod;
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

    @Query("select request from Dashboard request join request.user owner where owner.id <> :id and " +
            "(lower(request.senderBook.title) like lower(concat(:input, '%')) or " +
            "lower(request.senderBook.author) like lower(concat(:input, '%')) or " +
            "lower(request.user.location) like lower(concat(:input, '%'))) and " +
            "(:exchangeType is null or request.status = :exchangeType)")
    Page<Dashboard> getRequestBooksExceptOwnerIdByFilter(@Param("id") Long id, @Param("input") String input, @Param("exchangeType") ExchangeMethod exchangeType, Pageable pageable);

    @Query("select request from Dashboard request join request.user owner where owner.id <> :id")
    Page<Dashboard> getRequestBooksExceptOwnerId(@Param("id") Long id, Pageable pageable);


}
