package com.example.demo.domain.enumeration;

import lombok.Getter;

@Getter
public enum RequestStatus {
    PENDING,
    ACCEPTED,
    DECLINE,
    COMPLETED
}
