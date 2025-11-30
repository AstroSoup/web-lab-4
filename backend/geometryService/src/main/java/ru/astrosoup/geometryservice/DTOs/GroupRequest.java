package ru.astrosoup.geometryservice.DTOs;


import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class GroupRequest {
    JwtDto user;
    private Long id;
    private String name;
    private String description;
    private Set<Long> hits = new HashSet<>();
}
