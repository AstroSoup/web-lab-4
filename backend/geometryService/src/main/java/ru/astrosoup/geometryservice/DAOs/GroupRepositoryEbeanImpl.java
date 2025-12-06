package ru.astrosoup.geometryservice.DAOs;

import io.ebean.DB;
import io.ebean.Database;
import jakarta.enterprise.context.ApplicationScoped;
import ru.astrosoup.geometryservice.entities.GroupEntity;

import java.util.List;
import java.util.Optional;
import static java.util.Objects.isNull;


@ApplicationScoped
public class GroupRepositoryEbeanImpl implements GroupRepository {

    private Database db;

    public GroupRepositoryEbeanImpl() {
        this.db = DB.getDefault();
    }

    @Override
    public GroupEntity save(GroupEntity entity) {
        db.save(entity);
        return entity;
    }

    @Override
    public List<GroupEntity> findAll() {
        return db.find(GroupEntity.class).findList();
    }

    @Override
    public Optional<GroupEntity> findById(Long id) {
        GroupEntity entity = db.find(GroupEntity.class).setId(id).findOne();
        if (isNull(entity)) {
            return Optional.empty();
        }
        return Optional.of(entity);
    }

    @Override
    public List<GroupEntity> findByUserId(Long id) {
        return db.find(GroupEntity.class).where().eq("user_id", id).findList();
    }

    @Override
    public GroupEntity update(GroupEntity entity) {
        db.update(entity);
        return entity;
    }

    @Override
    public GroupEntity delete(GroupEntity entity) {
        db.delete(entity);
        return entity;
    }
}
