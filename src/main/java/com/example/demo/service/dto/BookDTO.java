package com.example.demo.service.dto;

import com.example.demo.domain.enumeration.BookCondition;
import com.example.demo.domain.enumeration.ExchangeMethod;
import com.example.demo.domain.enumeration.Genre;
import com.example.demo.domain.enumeration.Language;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO implements Serializable {

    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Size(max = 70, message = "Title must be at most 70 characters")
    private String title;

    @NotBlank(message = "Author cannot be empty")
    @Size(max = 50, message = "Author must be at most 50 characters")
    private String author;

    @NotNull(message = "Genre cannot be empty")
    private Genre genre;

    @NotNull(message = "Language cannot be empty")
    private Language language;

    @NotNull(message = "Condition cannot be empty")
    private BookCondition condition;

    @Size(max = 300, message = "Description must be at most 300 characters")
    private String description;

    private String photoBookUrl;

    private Boolean deleted;

    private UserDTO owner;
}
