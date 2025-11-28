package ru.astrosoup.authservice.DTOs;

import jakarta.enterprise.context.RequestScoped;
import lombok.Data;

@Data
@RequestScoped
public class JwtDto {
    private Long id;
    private String group;
}
