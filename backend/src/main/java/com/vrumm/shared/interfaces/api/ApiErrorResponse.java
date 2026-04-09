package com.vrumm.shared.interfaces.api;

import io.micronaut.serde.annotation.Serdeable;

import java.time.OffsetDateTime;

@Serdeable
public class ApiErrorResponse {

    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final OffsetDateTime timestamp;

    public ApiErrorResponse(int status, String error, String message, String path, OffsetDateTime timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
    }

    public static ApiErrorResponse of(int status, String error, String message, String path) {
        return new ApiErrorResponse(status, error, message, path, OffsetDateTime.now());
    }

    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }
    public String getPath() { return path; }
    public OffsetDateTime getTimestamp() { return timestamp; }
}
