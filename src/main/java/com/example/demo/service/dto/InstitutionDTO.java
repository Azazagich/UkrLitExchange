package com.example.demo.service.dto;

import com.example.demo.domain.User;
import com.example.demo.domain.enumeration.InstitutionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionDTO implements Serializable {

    private Long id;

    @NotBlank(message = "Institution name cannot be empty")
    @Size(max = 150, message = "Institution name must be at most 150 characters")
    private String name;

    @NotNull(message = "Institution type cannot be null")
    private InstitutionType type;

    @Size(max = 300, message = "Description must be at most 300 characters")
    private String description;

    private Set<UserDTO> participants;
}
