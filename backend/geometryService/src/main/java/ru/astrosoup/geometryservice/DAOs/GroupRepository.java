package ru.astrosoup.geometryservice.DAOs;

import ru.astrosoup.geometryservice.entities.GroupEntity;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    GroupEntity save(GroupEntity entity);
    List<GroupEntity> findAll();
    Optional<GroupEntity> findById(Long id);
    List<GroupEntity> findByUserId(Long id);
    GroupEntity update(GroupEntity entity);
    GroupEntity delete(GroupEntity entity);
}
