package ru.astrosoup.geometryservice.DAOs;

import ru.astrosoup.geometryservice.entities.HitEntity;
import ru.astrosoup.geometryservice.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface HitRepository {
    HitEntity save(HitEntity hit);
    Optional<HitEntity> findById(Long id);
    List<HitEntity> findByUser(UserEntity user);
    List<HitEntity> findAll();
}
