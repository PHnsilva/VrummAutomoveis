package com.vrumm.shared.interfaces.api;

import com.vrumm.shared.exception.DuplicateResourceException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
public class DuplicateResourceApiExceptionHandler extends AbstractApiExceptionHandler implements ExceptionHandler<DuplicateResourceException, HttpResponse<ApiErrorResponse>> {

    @Override
    public HttpResponse<ApiErrorResponse> handle(HttpRequest request, DuplicateResourceException exception) {
        return build(request, HttpStatus.CONFLICT, exception.getMessage());
    }
}
