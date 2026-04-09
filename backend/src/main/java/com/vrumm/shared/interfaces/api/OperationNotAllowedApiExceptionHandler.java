package com.vrumm.shared.interfaces.api;

import com.vrumm.shared.exception.OperationNotAllowedException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
public class OperationNotAllowedApiExceptionHandler extends AbstractApiExceptionHandler implements ExceptionHandler<OperationNotAllowedException, HttpResponse<ApiErrorResponse>> {

    @Override
    public HttpResponse<ApiErrorResponse> handle(HttpRequest request, OperationNotAllowedException exception) {
        return build(request, HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
