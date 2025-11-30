package ru.astrosoup.geometryservice.DTOs;

import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class GroupResponse {
    private Long id;
    private String name;
    private String description;
    private Set<Long> hits = new HashSet<>();
}
