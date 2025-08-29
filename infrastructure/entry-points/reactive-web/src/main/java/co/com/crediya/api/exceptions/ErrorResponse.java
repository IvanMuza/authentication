package co.com.crediya.api.exceptions;

public record ErrorResponse(String code, String message, String path) {}
