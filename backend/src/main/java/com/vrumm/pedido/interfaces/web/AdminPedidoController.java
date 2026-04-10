package com.vrumm.pedido.interfaces.web;

import com.vrumm.auth.application.facade.AuthSessionFacade;
import com.vrumm.cliente.domain.model.Cliente;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.CookieValue;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.Hidden;

import java.net.URI;
import java.util.Optional;

@Hidden
@Controller("/admin/pedidos")
public class AdminPedidoController {
    private final AuthSessionFacade authSessionFacade;

    public AdminPedidoController(AuthSessionFacade authSessionFacade) {
        this.authSessionFacade = authSessionFacade;
    }

    @Get
    public HttpResponse<?> redirecionar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) return HttpResponse.seeOther(URI.create("/"));
        if (autenticado.get().isEmpresa()) return HttpResponse.seeOther(URI.create("/empresa/pedidos"));
        if (autenticado.get().isBanco()) return HttpResponse.seeOther(URI.create("/banco/pedidos"));
        return HttpResponse.seeOther(URI.create("/perfil"));
    }
}
