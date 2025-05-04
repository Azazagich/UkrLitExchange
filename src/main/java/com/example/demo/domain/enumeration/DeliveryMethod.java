package com.example.demo.domain.enumeration;

import lombok.Getter;

//TODO delete
@Getter
public enum DeliveryMethod {
    PERSONAL_MEETING("Особиста зустріч"),
    SENDING_BY_NOVA_DELIVERY("Відправка Новою Поштою"),
    SENDING_BY_UKR_DELIVERY("Відправка Укрпоштою");

    private final String deliveryMethod;

    DeliveryMethod(String deliveryMethod){
        this.deliveryMethod = deliveryMethod;
    }
}
