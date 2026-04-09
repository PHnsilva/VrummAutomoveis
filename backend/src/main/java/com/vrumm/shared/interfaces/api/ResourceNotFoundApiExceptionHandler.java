package com.vrumm.shared.interfaces.api;

import com.vrumm.shared.exception.ResourceNotFoundException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
public class ResourceNotFoundApiExceptionHandler extends AbstractApiExceptionHandler implements ExceptionHandler<ResourceNotFoundException, HttpResponse<ApiErrorResponse>> {

    @Override
    public HttpResponse<ApiErrorResponse> handle(HttpRequest request, ResourceNotFoundException exception) {
        return build(request, HttpStatus.NOT_FOUND, exception.getMessage());
    }
}
