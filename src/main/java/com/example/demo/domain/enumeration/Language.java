package com.example.demo.domain.enumeration;

import lombok.Getter;

@Getter
public enum Language {
    UKRAINIAN("Українська"),
    ENGLISH("Англійська"),
    GERMAN("Німецька"),
    FRENCH("Французька"),
    POLISH("Польська"),
    SPANISH("Іспанська"),
    ITALIAN("Італійська"),
    RUSSIAN("Російська"),
    CHINESE("Китайська"),
    JAPANESE("Японська");

    private final String displayName;

    Language(String displayName) {
        this.displayName = displayName;
    }
}
