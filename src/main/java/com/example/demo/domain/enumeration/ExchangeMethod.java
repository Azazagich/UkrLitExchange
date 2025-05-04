package com.example.demo.domain.enumeration;

import lombok.Getter;

@Getter
public enum ExchangeMethod {
    EXCHANGE("Обмін"),
    DONATION("Подарунок");

    private final String exchangeMethod;

    ExchangeMethod(String exchangeMethod){
        this.exchangeMethod = exchangeMethod;
    }
}
