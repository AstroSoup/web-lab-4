package ru.astrosoup.geometryservice.entities;

import io.ebean.annotation.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "group_entity")
public class GroupEntity {
    @Id @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    private String description;
    @ManyToMany(mappedBy = "groups")
    private Set<HitEntity> hits = new HashSet<>();

    @Column(nullable = false, name = "user_id")
    private Long userId;

}
