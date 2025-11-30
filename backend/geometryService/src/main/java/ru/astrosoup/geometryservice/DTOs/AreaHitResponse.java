package ru.astrosoup.geometryservice.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AreaHitResponse {
    private long id;
    private int r;
    private int x;
    private float y;
    private boolean hit;
    private LocalDate date;
}
