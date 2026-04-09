package com.vrumm.shared.interfaces.api;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;

public abstract class AbstractApiExceptionHandler {

    protected HttpResponse<ApiErrorResponse> build(HttpRequest request, HttpStatus status, String message) {
        return HttpResponse.status(status)
                .body(ApiErrorResponse.of(status.getCode(), status.getReason(), message, request.getPath()));
    }
}
