package ru.astrosoup.geometryservice.DAOs;

import ru.astrosoup.geometryservice.entities.UserEntity;

import java.util.Optional;

public interface UserRepository {
    UserEntity save(UserEntity user);
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByName(String name);
    UserEntity update(UserEntity user);
    UserEntity delete(UserEntity user);
}
