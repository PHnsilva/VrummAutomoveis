package com.vrumm.controller.api;

import com.vrumm.controller.AuthController;
import com.vrumm.domain.Cliente;
import com.vrumm.dto.ClienteForm;
import com.vrumm.dto.auth.AuthResponse;
import com.vrumm.dto.auth.LoginForm;
import com.vrumm.service.ClienteService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.CookieValue;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.cookie.Cookie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.Optional;

@Tag(name = "auth")
@Controller("/api/auth")
public class AuthApiController {

    private final ClienteService clienteService;
    private final AuthController authController;

    public AuthApiController(ClienteService clienteService, AuthController authController) {
        this.clienteService = clienteService;
        this.authController = authController;
    }

    @Operation(summary = "Cadastra um novo cliente e autentica o usuário")
    @Post("/cadastro")
    public HttpResponse<AuthResponse> cadastrar(@Body @Valid ClienteForm form) {
        Cliente cliente = clienteService.salvar(form);
        AuthResponse response = AuthResponse.fromCliente(cliente, true, "Cadastro realizado com sucesso");
        return HttpResponse.created(response)
                .cookie(authController.buildAuthCookie(cliente.getId()));
    }

    @Operation(summary = "Autentica um cliente por e-mail e senha")
    @Post("/login")
    public HttpResponse<AuthResponse> login(@Body @Valid LoginForm form) {
        Optional<Cliente> clienteOpt = clienteService.autenticar(form.getEmail(), form.getSenha());
        if (clienteOpt.isEmpty()) {
            return HttpResponse.unauthorized();
        }

        Cliente cliente = clienteOpt.get();
        AuthResponse response = AuthResponse.fromCliente(cliente, true, "Login realizado com sucesso");
        return HttpResponse.ok(response)
                .cookie(authController.buildAuthCookie(cliente.getId()));
    }

    @Operation(summary = "Obtém o cliente autenticado pelo cookie")
    @Get("/me")
    public HttpResponse<AuthResponse> me(@CookieValue(value = AuthController.AUTH_COOKIE) Optional<Long> clienteId) {
        Optional<Cliente> clienteOpt = clienteId.flatMap(clienteService::buscarPorId);
        if (clienteOpt.isEmpty()) {
            return HttpResponse.unauthorized();
        }
        return HttpResponse.ok(AuthResponse.fromCliente(clienteOpt.get(), true, "Cliente autenticado"));
    }

    @Operation(summary = "Encerra a autenticação do cliente")
    @Post("/logout")
    public HttpResponse<Void> logout() {
        Cookie expired = Cookie.of(AuthController.AUTH_COOKIE, "")
                .path("/")
                .httpOnly(true)
                .maxAge(0);
        return HttpResponse.<Void>ok().cookie(expired);
    }
}
