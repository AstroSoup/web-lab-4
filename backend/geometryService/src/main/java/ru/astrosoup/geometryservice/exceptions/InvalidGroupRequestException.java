package ru.astrosoup.geometryservice.exceptions;

public class InvalidGroupRequestException extends RuntimeException {
    public InvalidGroupRequestException(String message) {
        super(message);
    }
}
