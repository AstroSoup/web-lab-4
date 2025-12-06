package ru.astrosoup.geometryservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "hit_entity")
public class HitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int r;
    private int x;
    private float y;
    private boolean hit;
    private LocalDate date = LocalDate.now();

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @ManyToMany
    private Set<GroupEntity> groups;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HitEntity hitEntity = (HitEntity) o;
        return getId() != null && Objects.equals(getId(), hitEntity.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
