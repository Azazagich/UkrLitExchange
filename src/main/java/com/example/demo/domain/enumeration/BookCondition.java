package com.example.demo.domain.enumeration;

import lombok.Getter;

@Getter
public enum BookCondition {
    NEW("Нова"),
    LIKE_NEW("Майже нова"),
    GOOD("Хороший стан"),
    ACCEPTABLE("Прийнятний стан"),
    DAMAGED("Пошкоджена"),
    MISSING_PAGES("Відсутні сторінки");

    private final String displayName;

    BookCondition(String displayName) {
        this.displayName = displayName;
    }
}
