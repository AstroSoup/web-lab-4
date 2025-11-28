package ru.astrosoup.authservice.DAOs;

import ru.astrosoup.authservice.entities.UserEntity;

import java.util.Optional;

public interface UserRepository {
    UserEntity save(UserEntity user);
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByName(String name);
    UserEntity update(UserEntity user);
    UserEntity delete(UserEntity user);
}
