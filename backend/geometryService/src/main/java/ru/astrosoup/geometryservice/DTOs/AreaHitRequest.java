package ru.astrosoup.geometryservice.DTOs;

import lombok.Data;

@Data
public class AreaHitRequest {
    private int r;
    private int x;
    private float y;
    private Long id;
    private JwtDto user;
}
