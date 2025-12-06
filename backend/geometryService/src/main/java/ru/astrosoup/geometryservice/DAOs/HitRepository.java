package ru.astrosoup.geometryservice.DAOs;

import ru.astrosoup.geometryservice.entities.HitEntity;

import java.util.List;
import java.util.Optional;

public interface HitRepository {
    HitEntity save(HitEntity hit);
    Optional<HitEntity> findById(Long id);
    List<HitEntity> findByUserId(Long id);
    List<HitEntity> findAll();
    HitEntity update(HitEntity hit);
    HitEntity delete(HitEntity hit);
}
