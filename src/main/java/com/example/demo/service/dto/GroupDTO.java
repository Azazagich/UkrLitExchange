package com.example.demo.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO implements Serializable {

    private Long id;

    @NotBlank(message = "Group name cannot be empty")
    @Size(max = 50, message = "Group name must be at most 50 characters")
    private String name;

    @Size(max = 350, message = "Description must be at most 350 characters")
    private String description;

    private Set<UserDTO> participants;
}
