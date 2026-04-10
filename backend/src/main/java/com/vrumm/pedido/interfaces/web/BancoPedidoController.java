package com.vrumm.pedido.interfaces.web;

import com.vrumm.auth.application.facade.AuthSessionFacade;
import com.vrumm.cliente.domain.model.Cliente;
import com.vrumm.pedido.application.dto.ContratoCreditoForm;
import com.vrumm.pedido.application.dto.FinanceiroAvaliacaoForm;
import com.vrumm.pedido.application.facade.PedidoFacade;
import com.vrumm.pedido.domain.model.PedidoStatus;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.CookieValue;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.ModelAndView;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Hidden
@Controller("/banco/pedidos")
public class BancoPedidoController {
    private final PedidoFacade pedidoFacade;
    private final AuthSessionFacade authSessionFacade;

    public BancoPedidoController(PedidoFacade pedidoFacade, AuthSessionFacade authSessionFacade) {
        this.pedidoFacade = pedidoFacade;
        this.authSessionFacade = authSessionFacade;
    }

    @Get
    public HttpResponse<?> listar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                  @QueryValue Optional<String> status,
                                  @QueryValue Optional<String> sucesso,
                                  @QueryValue Optional<String> erro) {
        Optional<Cliente> banco = getBanco(clienteId);
        if (banco.isEmpty()) return HttpResponse.seeOther(URI.create("/perfil"));
        Optional<PedidoStatus> statusFiltro = parseStatus(status.orElse(null));
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Painel do banco");
        model.put("cliente", banco.get());
        model.put("pedidos", pedidoFacade.listarPedidosBanco(statusFiltro.orElse(null)));
        model.put("statusDisponiveis", PedidoStatus.values());
        model.put("statusAtual", status.orElse("TODOS"));
        model.put("sucesso", sucesso.filter("avaliacao"::equals).isPresent() ? "Avaliação financeira registrada com sucesso." : sucesso.filter("credito"::equals).isPresent() ? "Contrato de crédito registrado com sucesso." : null);
        model.put("erro", erro.filter("acao"::equals).isPresent() ? "Não foi possível concluir a ação financeira." : null);
        return HttpResponse.ok(new ModelAndView<>("pedidos/banco", model));
    }

    @Post("/{id}/avaliar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> avaliar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                   @PathVariable Long id,
                                   @Body @Valid FinanceiroAvaliacaoForm form) {
        Optional<Cliente> banco = getBanco(clienteId);
        if (banco.isEmpty()) return HttpResponse.seeOther(URI.create("/perfil"));
        try {
            pedidoFacade.avaliarFinanceiramente(id, banco.get().getBancoId(), form);
            return HttpResponse.seeOther(URI.create("/pedidos/" + id + "?sucesso=avaliacao"));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return HttpResponse.seeOther(URI.create("/pedidos/" + id + "?erro=avaliacao"));
        }
    }

    @Post("/{id}/credito")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> registrarCredito(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                            @PathVariable Long id,
                                            @Body @Valid ContratoCreditoForm form) {
        Optional<Cliente> banco = getBanco(clienteId);
        if (banco.isEmpty()) return HttpResponse.seeOther(URI.create("/perfil"));
        try {
            pedidoFacade.salvarContratoCredito(id, banco.get().getBancoId(), form);
            return HttpResponse.seeOther(URI.create("/pedidos/" + id + "?sucesso=credito"));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return HttpResponse.seeOther(URI.create("/pedidos/" + id + "?erro=credito"));
        }
    }

    private Optional<Cliente> getBanco(Optional<Long> clienteId) {
        return clienteId.flatMap(authSessionFacade::getClienteAutenticado).filter(Cliente::isBanco);
    }

    private Optional<PedidoStatus> parseStatus(String status) {
        if (status == null || status.isBlank() || "TODOS".equalsIgnoreCase(status)) return Optional.empty();
        try { return Optional.of(PedidoStatus.valueOf(status)); } catch (IllegalArgumentException e) { return Optional.empty(); }
    }
}
