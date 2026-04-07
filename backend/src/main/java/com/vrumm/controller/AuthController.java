package com.vrumm.controller;

import com.vrumm.domain.Cliente;
import com.vrumm.dto.ClienteForm;
import com.vrumm.dto.auth.LoginForm;
import com.vrumm.service.ClienteService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.cookie.Cookie;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;

import java.net.URI;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Hidden
@Controller
public class AuthController {

    public static final String AUTH_COOKIE = "vrumm_cliente_id";

    private final ClienteService clienteService;

    public AuthController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Post("/cadastro")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public MutableHttpResponse<?> cadastrar(@Body @Valid ClienteForm form) {
        try {
            Cliente cliente = clienteService.salvar(form);
            return HttpResponse.seeOther(URI.create("/home"))
                    .cookie(buildAuthCookie(cliente.getId()));
        } catch (IllegalArgumentException e) {
            return HttpResponse.seeOther(URI.create("/?erro=cadastro"));
        }
    }

    @Post("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public MutableHttpResponse<?> login(@Body @Valid LoginForm form) {
        Optional<Cliente> clienteOpt = clienteService.autenticar(form.getEmail(), form.getSenha());

        if (clienteOpt.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/?erro=login"));
        }

        return HttpResponse.seeOther(URI.create("/home"))
                .cookie(buildAuthCookie(clienteOpt.get().getId()));
    }

    @Get("/logout")
    public MutableHttpResponse<?> logout() {
        Cookie expired = Cookie.of(AUTH_COOKIE, "")
                .path("/")
                .httpOnly(true)
                .maxAge(0);

        return HttpResponse.seeOther(URI.create("/"))
                .cookie(expired);
    }

    public Optional<Cliente> getClienteAutenticado(Long clienteId) {
        if (clienteId == null) {
            return Optional.empty();
        }
        return clienteService.buscarPorId(clienteId);
    }

    public Cookie buildAuthCookie(Long clienteId) {
        return Cookie.of(AUTH_COOKIE, String.valueOf(clienteId))
                .httpOnly(true)
                .path("/")
                .maxAge((int) ChronoUnit.DAYS.getDuration().multipliedBy(7).getSeconds());
    }
}
