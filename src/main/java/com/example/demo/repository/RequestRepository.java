package com.example.demo.repository;

import com.example.demo.domain.Request;
import com.example.demo.domain.enumeration.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllOrdersBySenderId(Long senderId);

    @Query("SELECT request FROM Request request WHERE (request.sender.id = :userId OR request.receiver.id = :userId) AND request.requestStatus IN :status")
    List<Request> findByRequestStatusAndUserInvolved(@Param("status") List<RequestStatus> status, @Param("userId") Long userId);

    @Query("SELECT request FROM Request request WHERE (request.sender.id = :userId OR request.receiver.id = :userId)")
    List<Request> findAllByUserId(@Param("userId") Long userId);
}
