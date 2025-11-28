package ru.astrosoup.authservice.exceptions;

public class LoginIsNotValidException extends RuntimeException {
    public LoginIsNotValidException(String message) {
        super(message);
    }
}
