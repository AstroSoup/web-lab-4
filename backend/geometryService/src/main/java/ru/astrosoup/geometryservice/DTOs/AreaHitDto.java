package ru.astrosoup.geometryservice.DTOs;

import lombok.Data;

@Data
public class AreaHitDto {
    JwtDto user;
    private int r;
    private int x;
    private float y;

    public AreaHitDto(JwtDto user, AreaHitRequest areaHitRequest) {
        this.user = user;
        this.r = areaHitRequest.getR();
        this.x = areaHitRequest.getX();
        this.y = areaHitRequest.getY();
    }

}
