package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "groups")
public class Group extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column
    private String description;

    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name = "groups_participants",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "participants_id")
    )
    private Set<User> participants;
}