package com.example.demo.domain.enumeration;

import lombok.Getter;

@Getter
public enum Genre {
    FICTION("Художня література"),
    NON_FICTION("Нехудожня література"),
    FANTASY("Фентезі"),
    SCIENCE_FICTION("Наукова фантастика"),
    MYSTERY("Детектив"),
    ROMANCE("Романтика"),
    HISTORICAL("Історична література"),
    BIOGRAPHY("Біографія"),
    CHILDREN("Дитяча література"),
    DRAMA("Драма"),
    HORROR("Жахи"),
    POETRY("Поезія"),
    CLASSIC("Класика"),
    EDUCATIONAL("Навчальна література");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }
}
