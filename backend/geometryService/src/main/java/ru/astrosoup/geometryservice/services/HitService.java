package ru.astrosoup.geometryservice.services;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import ru.astrosoup.geometryservice.DAOs.HitRepositoryEbeanImpl;
import ru.astrosoup.geometryservice.DTOs.AreaHitRequest;
import ru.astrosoup.geometryservice.DTOs.JwtDto;
import ru.astrosoup.geometryservice.DTOs.AreaHitDto;
import ru.astrosoup.geometryservice.DTOs.AreaHitResponse;
import ru.astrosoup.geometryservice.entities.HitEntity;
import ru.astrosoup.geometryservice.exceptions.InvalidHitRequestException;
import ru.astrosoup.geometryservice.exceptions.UserDoesNotExistException;

import java.util.*;
import java.util.logging.Logger;


@Dependent
@Stateless
public class HitService {

    @Inject
    HitRepositoryEbeanImpl hitRepository;


    public AreaHitResponse addHit(AreaHitDto request) throws UserDoesNotExistException, InvalidHitRequestException {

        if (!validateRequest(request)) {
            throw new InvalidHitRequestException("Data in hit request isn`t valid.");
        }

        HitEntity hitEntity = new HitEntity();

        hitEntity.setY(request.getY());
        hitEntity.setX(request.getX());
        hitEntity.setR(request.getR());


        hitEntity.setUserId(request.getUser().getId());
        hitEntity.setHit(isAreaHit(request));
        hitRepository.save(hitEntity);
        AreaHitResponse response = new AreaHitResponse();
        response.setHit(hitEntity.isHit());
        response.setR(hitEntity.getR());
        response.setY(hitEntity.getY());
        response.setX(hitEntity.getX());
        response.setDate(hitEntity.getDate());
        response.setId(hitEntity.getId());
        return response;
    }

    public List<AreaHitResponse> getHits(JwtDto user) throws  UserDoesNotExistException {

        List<HitEntity> hitEntities = hitRepository.findByUserId(user.getId());
        List<AreaHitResponse> result = new ArrayList<>();
        for (HitEntity hitEntity : hitEntities) {
            AreaHitResponse areaHitResponse = new AreaHitResponse();
            areaHitResponse.setY(hitEntity.getY());
            areaHitResponse.setX(hitEntity.getX());
            areaHitResponse.setR(hitEntity.getR());
            areaHitResponse.setHit(hitEntity.isHit());
            areaHitResponse.setDate(hitEntity.getDate());
            areaHitResponse.setId(hitEntity.getId());

            result.add(areaHitResponse);
        }
        return result;
    }

    private boolean isAreaHit(AreaHitDto request) {
        int r = request.getR();
        float x = request.getX();
        float y = request.getY();

        if (x > 0 && y < 0) return false;
        if (x >= 0 && y >= 0) return Math.sqrt(x * x + y * y) <= r;
        if (x < 0 && y >= 0) return x >= -r/2f && y <= r;
        if (x <= 0 && y < 0) return y >= -x - r/2f;
        return false;
    }
    private boolean validateRequest(AreaHitDto request) {
        return Arrays.asList(1, 2, 3, 4, 5).contains(request.getR())
                && Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3).contains(request.getX())
                && request.getY() >= -3 && request.getY() <= 5;
    }

    public void delete(AreaHitRequest request) throws InvalidHitRequestException {

        Optional<HitEntity> oEntity = hitRepository.findById(request.getId());
        if (oEntity.isEmpty()) {
            throw new InvalidHitRequestException("Entity not found.");
        }
        HitEntity entity = oEntity.get();
        if (!request.getUser().getId().equals(entity.getUserId())) {
            throw new InvalidHitRequestException("Entity not found.");
        }
        hitRepository.delete(entity);
    }

}
