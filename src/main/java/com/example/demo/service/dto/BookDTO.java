package com.example.demo.service.dto;

import com.example.demo.domain.enumeration.BookStatus;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
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

    @NotBlank(message = "Genre cannot be empty")
    @Size(max = 50, message = "Genre must be at most 50 characters")
    private String genre;

    @NotBlank(message = "Language cannot be empty")
    @Size(max = 50, message = "Language must be at most 50 characters")
    private String language;

    @NotNull(message = "Book status cannot be null")
    private BookStatus bookStatus;

    @NotBlank(message = "Condition cannot be empty")
    @Size(max = 30, message = "Condition must be at most 30 characters")
    private String condition;

    @Size(max = 300, message = "Description must be at most 300 characters")
    private String description;

    private String photoBookUrl;

    private UserDTO owner;
}
