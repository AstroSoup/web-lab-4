package ru.astrosoup.authservice.DTOs;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
