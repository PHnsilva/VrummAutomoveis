package com.vrumm.shared.interfaces.web;

import com.vrumm.shared.interfaces.api.ApiErrorResponse;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.context.annotation.Replaces;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolationException;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Produces
@Singleton
@Replaces(io.micronaut.validation.exceptions.ConstraintExceptionHandler.class)
public class CrudValidationExceptionHandler implements ExceptionHandler<ConstraintViolationException, HttpResponse<?>> {

    private static final Pattern EDITAR_CLIENTE = Pattern.compile("^/clientes/(\\d+)/editar$");
    private static final String EDITAR_PERFIL = "/perfil/editar";

    @Override
    public HttpResponse<?> handle(HttpRequest request, ConstraintViolationException exception) {
        String message = exception.getConstraintViolations().stream()
                .map(violation -> violation.getMessage())
                .distinct()
                .collect(Collectors.joining("; "));
        String finalMessage = message.isBlank() ? "Dados inválidos" : message;
        String path = request.getPath();

        if (path.startsWith("/api/")) {
            return HttpResponse.badRequest(ApiErrorResponse.of(
                    HttpStatus.BAD_REQUEST.getCode(),
                    HttpStatus.BAD_REQUEST.getReason(),
                    finalMessage,
                    path));
        }

        if (EDITAR_PERFIL.equals(path)) {
            String encoded = URLEncoder.encode(finalMessage, StandardCharsets.UTF_8);
            return HttpResponse.seeOther(URI.create(EDITAR_PERFIL + "?erro=" + encoded));
        }

        if (!path.startsWith("/clientes")) {
            return HttpResponse.badRequest(ApiErrorResponse.of(
                    HttpStatus.BAD_REQUEST.getCode(),
                    HttpStatus.BAD_REQUEST.getReason(),
                    finalMessage,
                    path));
        }

        String redirectPath = "/clientes";
        if ("/clientes".equals(path)) {
            redirectPath = "/clientes/novo";
        }
        if ("/clientes/novo".equals(path)) {
            redirectPath = path;
        }

        Matcher matcher = EDITAR_CLIENTE.matcher(path);
        if (matcher.matches()) {
            redirectPath = path;
        }

        String encoded = URLEncoder.encode(finalMessage, StandardCharsets.UTF_8);
        return HttpResponse.seeOther(URI.create(redirectPath + "?erro=" + encoded));
    }
}
