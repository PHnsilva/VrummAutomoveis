package com.vrumm.auth.interfaces.api;

import com.vrumm.auth.application.dto.AuthResponse;
import com.vrumm.auth.application.dto.LoginForm;
import com.vrumm.auth.application.facade.AuthSessionFacade;
import com.vrumm.cliente.application.dto.ClienteForm;
import com.vrumm.cliente.application.facade.ClienteFacade;
import com.vrumm.cliente.domain.model.Cliente;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.CookieValue;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.Optional;

@Tag(name = "auth")
@Controller("/api/auth")
public class AuthApiController {

    private final ClienteFacade clienteFacade;
    private final AuthSessionFacade authSessionFacade;

    public AuthApiController(ClienteFacade clienteFacade, AuthSessionFacade authSessionFacade) {
        this.clienteFacade = clienteFacade;
        this.authSessionFacade = authSessionFacade;
    }

    @Operation(summary = "Cadastra um novo cliente e autentica o usuário")
    @Post("/cadastro")
    public HttpResponse<AuthResponse> cadastrar(@Body @Valid ClienteForm form) {
        Cliente cliente = clienteFacade.salvar(form);
        AuthResponse response = AuthResponse.fromCliente(cliente, true, "Cadastro realizado com sucesso");
        return HttpResponse.created(response)
                .cookie(authSessionFacade.buildAuthCookie(cliente.getId()));
    }

    @Operation(summary = "Autentica um cliente por e-mail e senha")
    @Post("/login")
    public HttpResponse<AuthResponse> login(@Body @Valid LoginForm form) {
        Optional<Cliente> clienteOpt = clienteFacade.autenticar(form.getEmail(), form.getSenha());
        if (clienteOpt.isEmpty()) {
            return HttpResponse.unauthorized();
        }

        Cliente cliente = clienteOpt.get();
        AuthResponse response = AuthResponse.fromCliente(cliente, true, "Login realizado com sucesso");
        return HttpResponse.ok(response)
                .cookie(authSessionFacade.buildAuthCookie(cliente.getId()));
    }

    @Operation(summary = "Obtém o cliente autenticado pelo cookie")
    @Get("/me")
    public HttpResponse<AuthResponse> me(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId) {
        Optional<Cliente> clienteOpt = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (clienteOpt.isEmpty()) {
            return HttpResponse.unauthorized();
        }
        return HttpResponse.ok(AuthResponse.fromCliente(clienteOpt.get(), true, "Cliente autenticado"));
    }

    @Operation(summary = "Encerra a autenticação do cliente")
    @Post("/logout")
    public HttpResponse<Void> logout() {
        return HttpResponse.<Void>ok().cookie(authSessionFacade.buildExpiredAuthCookie());
    }
}
