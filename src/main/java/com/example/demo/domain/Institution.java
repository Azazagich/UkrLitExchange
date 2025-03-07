package com.example.demo.domain;

import com.example.demo.domain.enumeration.InstitutionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "institution")
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 150, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InstitutionType type;

    @Column
    private String description;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "institution")
    private Set<User> participants;
}
