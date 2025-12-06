package ru.astrosoup.geometryservice.DAOs;

import io.ebean.DB;
import io.ebean.Database;
import jakarta.enterprise.context.ApplicationScoped;
import ru.astrosoup.geometryservice.entities.HitEntity;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class HitRepositoryEbeanImpl implements HitRepository {

    private final Database db;

    public HitRepositoryEbeanImpl() {
        this.db = DB.getDefault();
    }

    public HitEntity save(HitEntity hit) {
        db.save(hit);
        return hit;
    }

    public Optional<HitEntity> findById(Long id) {
        return Optional.ofNullable(db.find(HitEntity.class, id));
    }

    @Override
    public List<HitEntity> findByUserId(Long id) {
        return db.find(HitEntity.class).where().eq("user_id", id).findList();
    }

    public List<HitEntity> findAll() {
        return db.find(HitEntity.class).findList();
    }

    @Override
    public HitEntity update(HitEntity hit) {
        db.update(hit);
        return hit;
    }

    @Override
    public HitEntity delete(HitEntity hit) {
        db.delete(hit);
        return hit;
    }
}
