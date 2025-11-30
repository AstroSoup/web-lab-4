package ru.astrosoup.geometryservice.DAOs;

import ru.astrosoup.geometryservice.DTOs.GroupRequest;
import ru.astrosoup.geometryservice.entities.GroupEntity;
import ru.astrosoup.geometryservice.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    GroupEntity save(GroupEntity entity);
    List<GroupEntity> findAll();
    Optional<GroupEntity> findById(Long id);
    List<GroupEntity> findByUser(UserEntity user);
    GroupEntity update(GroupEntity entity);
    GroupEntity delete(GroupEntity entity);
}
