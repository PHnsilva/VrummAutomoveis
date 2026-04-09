package com.vrumm.shared.interfaces.api;

import java.lang.IllegalStateException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
public class IllegalStateApiExceptionHandler extends AbstractApiExceptionHandler implements ExceptionHandler<IllegalStateException, HttpResponse<ApiErrorResponse>> {

    @Override
    public HttpResponse<ApiErrorResponse> handle(HttpRequest request, IllegalStateException exception) {
        return build(request, HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
