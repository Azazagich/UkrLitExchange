package com.example.demo.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private Long id;

    @NotBlank(message = "Username cannot be empty")
    @Size(max = 30, message = "Username must be at most 30 characters")
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Full name cannot be empty")
    private String fullName;

    @NotBlank(message = "Location cannot be empty")
    private String location;

    @Size(max = 350, message = "Comment must be at most 350 characters")
    private String bio;

    private Set<BookDTO> books;

    private Set<GroupDTO> groups;

    private InstitutionDTO institution;
}
