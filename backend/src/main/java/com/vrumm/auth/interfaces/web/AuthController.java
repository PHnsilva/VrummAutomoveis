package com.vrumm.auth.interfaces.web;

import com.vrumm.auth.application.dto.LoginForm;
import com.vrumm.auth.application.facade.AuthSessionFacade;
import com.vrumm.cliente.application.dto.ClienteForm;
import com.vrumm.cliente.application.facade.ClienteFacade;
import com.vrumm.cliente.domain.model.Cliente;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.Optional;

@Hidden
@Controller
public class AuthController {

    private final ClienteFacade clienteFacade;
    private final AuthSessionFacade authSessionFacade;

    public AuthController(ClienteFacade clienteFacade, AuthSessionFacade authSessionFacade) {
        this.clienteFacade = clienteFacade;
        this.authSessionFacade = authSessionFacade;
    }

    @Post("/cadastro")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public MutableHttpResponse<?> cadastrar(@Body @Valid ClienteForm form) {
        try {
            Cliente cliente = clienteFacade.salvar(form);
            return HttpResponse.seeOther(URI.create("/home"))
                    .cookie(authSessionFacade.buildAuthCookie(cliente.getId()));
        } catch (IllegalArgumentException e) {
            return HttpResponse.seeOther(URI.create("/?erro=cadastro"));
        }
    }

    @Post("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public MutableHttpResponse<?> login(@Body @Valid LoginForm form) {
        Optional<Cliente> clienteOpt = clienteFacade.autenticar(form.getEmail(), form.getSenha());
        if (clienteOpt.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/?erro=login"));
        }

        return HttpResponse.seeOther(URI.create("/home"))
                .cookie(authSessionFacade.buildAuthCookie(clienteOpt.get().getId()));
    }

    @Get("/logout")
    public MutableHttpResponse<?> logout() {
        return HttpResponse.seeOther(URI.create("/"))
                .cookie(authSessionFacade.buildExpiredAuthCookie());
    }
}
