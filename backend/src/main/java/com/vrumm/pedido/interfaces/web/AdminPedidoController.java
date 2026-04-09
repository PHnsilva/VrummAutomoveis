package com.vrumm.pedido.interfaces.web;

import com.vrumm.auth.application.facade.AuthSessionFacade;
import com.vrumm.cliente.domain.model.Cliente;
import com.vrumm.pedido.application.facade.PedidoFacade;
import com.vrumm.pedido.domain.model.PedidoStatus;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.CookieValue;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.ModelAndView;
import io.swagger.v3.oas.annotations.Hidden;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Hidden
@Controller("/admin/pedidos")
public class AdminPedidoController {

    private final PedidoFacade pedidoFacade;
    private final AuthSessionFacade authSessionFacade;

    public AdminPedidoController(PedidoFacade pedidoFacade, AuthSessionFacade authSessionFacade) {
        this.pedidoFacade = pedidoFacade;
        this.authSessionFacade = authSessionFacade;
    }

    @Get
    public HttpResponse<?> listar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                  @QueryValue Optional<String> status,
                                  @QueryValue Optional<String> sucesso,
                                  @QueryValue Optional<String> erro) {
        Optional<Cliente> adminOpt = getAdmin(clienteId);
        if (adminOpt.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/perfil"));
        }

        PedidoStatus filtro = status.flatMap(this::parseStatus).orElse(null);
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Painel de Pedidos");
        model.put("cliente", adminOpt.get());
        model.put("pedidos", pedidoFacade.listarPedidosParaAdmin(filtro));
        model.put("statusAtual", filtro == null ? "TODOS" : filtro.name());
        model.put("statusDisponiveis", Arrays.asList(PedidoStatus.values()));
        model.put("sucesso", mapearSucesso(sucesso));
        model.put("erro", mapearErro(erro));
        return HttpResponse.ok(new ModelAndView<>("pedidos/admin", model));
    }

    @Post("/{id}/recusar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> recusar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                   @PathVariable Long id,
                                   @QueryValue Optional<String> status) {
        if (getAdmin(clienteId).isEmpty()) {
            return HttpResponse.seeOther(URI.create("/perfil"));
        }

        try {
            pedidoFacade.recusarPedido(id);
            return HttpResponse.seeOther(URI.create(buildAdminRedirect(status, "recusa", null)));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return HttpResponse.seeOther(URI.create(buildAdminRedirect(status, null, "acao")));
        }
    }

    private Optional<Cliente> getAdmin(Optional<Long> clienteId) {
        return clienteId
                .flatMap(authSessionFacade::getClienteAutenticado)
                .filter(Cliente::isAdmin);
    }

    private Optional<PedidoStatus> parseStatus(String status) {
        if (status == null || status.isBlank() || "TODOS".equalsIgnoreCase(status)) {
            return Optional.empty();
        }
        try {
            return Optional.of(PedidoStatus.valueOf(status));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private String buildAdminRedirect(Optional<String> status, String sucesso, String erro) {
        StringBuilder redirect = new StringBuilder("/admin/pedidos");
        boolean possuiQuery = false;
        if (status.isPresent() && !status.get().isBlank() && !"TODOS".equalsIgnoreCase(status.get())) {
            redirect.append("?status=").append(status.get());
            possuiQuery = true;
        }
        if (sucesso != null) {
            redirect.append(possuiQuery ? '&' : '?').append("sucesso=").append(sucesso);
            possuiQuery = true;
        }
        if (erro != null) {
            redirect.append(possuiQuery ? '&' : '?').append("erro=").append(erro);
        }
        return redirect.toString();
    }

    private String mapearSucesso(Optional<String> sucesso) {
        if (sucesso.filter("pedido-criado"::equals).isPresent()) {
            return "Pedido de aluguel criado com sucesso.";
        }
        if (sucesso.filter("recusa"::equals).isPresent()) {
            return "Pedido recusado com sucesso.";
        }
        return null;
    }

    private String mapearErro(Optional<String> erro) {
        return erro.filter("acao"::equals).isPresent()
                ? "Não foi possível atualizar o status do pedido."
                : null;
    }
}
