package com.example.demo.domain.enumeration;

import lombok.Getter;


// TODO delete getter
@Getter
public enum BookCondition {
    NEW("Нова"),
    LIKE_NEW("Як нова"),
    GOOD("Хороший стан"),
    ACCEPTABLE("Прийнятний стан"),
    DAMAGED("Пошкоджена"),
    MISSING_PAGES("Відсутні сторінки");

    private final String condition;

    BookCondition(String condition){
        this.condition = condition;
    }
}
