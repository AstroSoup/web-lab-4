package ru.astrosoup.authservice.DAOs;

import io.ebean.DB;
import io.ebean.Database;
import jakarta.enterprise.context.ApplicationScoped;
import ru.astrosoup.authservice.entities.UserEntity;

import java.util.Optional;

@ApplicationScoped
public class UserRepositoryEbeanImpl implements UserRepository {

    private Database db;


    public UserRepositoryEbeanImpl() {
        this.db = DB.getDefault();
    }

    @Override
    public UserEntity save(UserEntity user) {
        db.save(user);
        return user;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return Optional.ofNullable(db.find(UserEntity.class, id));
    }

    @Override
    public Optional<UserEntity> findByName(String name) {
        return Optional.ofNullable(db.find(UserEntity.class)
                .where().eq("username", name)
                .findOne());
    }

    @Override
    public UserEntity update(UserEntity user) {
        db.update(user);
        return user;
    }

    @Override
    public UserEntity delete(UserEntity user) {
        db.delete(user);
        return user;
    }


}

