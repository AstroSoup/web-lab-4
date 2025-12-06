package ru.astrosoup.geometryservice.services;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.TimeUnit;
import static java.util.Objects.isNull;

// TODO: PREFERABLY REFACTOR INTO JWTSERVICE
@ApplicationScoped
public class RevocationService {
    private Cache<String, String> revoked = Caffeine.newBuilder()
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .build();

    public void revoke(String token) {
        revoked.put(token, token);
    }

    public boolean isRevoked(String token) {
        return !isNull(revoked.getIfPresent(token));
    }

}
