package com.vrumm.pedido.interfaces.web;

import com.vrumm.auth.application.facade.AuthSessionFacade;
import com.vrumm.contrato.domain.model.TipoProprietario;
import com.vrumm.cliente.domain.model.Cliente;
import com.vrumm.pedido.application.dto.ContratoForm;
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
@Controller("/empresa/pedidos")
public class EmpresaPedidoController {
    private final PedidoFacade pedidoFacade;
    private final AuthSessionFacade authSessionFacade;

    public EmpresaPedidoController(PedidoFacade pedidoFacade, AuthSessionFacade authSessionFacade) {
        this.pedidoFacade = pedidoFacade;
        this.authSessionFacade = authSessionFacade;
    }

    @Get
    public HttpResponse<?> listar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                  @QueryValue Optional<String> status,
                                  @QueryValue Optional<String> sucesso,
                                  @QueryValue Optional<String> erro) {
        Optional<Cliente> empresa = getEmpresa(clienteId);
        if (empresa.isEmpty()) return HttpResponse.seeOther(URI.create("/perfil"));
        Optional<PedidoStatus> statusFiltro = parseStatus(status.orElse(null));
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Painel da empresa");
        model.put("cliente", empresa.get());
        model.put("pedidos", pedidoFacade.listarPedidosEmpresa(statusFiltro.orElse(null)));
        model.put("statusDisponiveis", PedidoStatus.values());
        model.put("statusAtual", status.orElse("TODOS"));
        model.put("sucesso", sucesso.filter("cancelado"::equals).isPresent() ? "Pedido cancelado com sucesso." : null);
        model.put("erro", erro.filter("acao"::equals).isPresent() ? "Não foi possível concluir a ação operacional." : null);
        return HttpResponse.ok(new ModelAndView<>("pedidos/empresa", model));
    }

    @Post("/{id}/contrato")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> salvarContrato(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                          @PathVariable Long id,
                                          @Body @Valid ContratoForm form) {
        if (getEmpresa(clienteId).isEmpty()) return HttpResponse.seeOther(URI.create("/perfil"));
        try {
            if (form.getTipoContrato() == null) {
                return HttpResponse.seeOther(URI.create("/pedidos/" + id + "?sucesso=contrato"));
            }
            form.setTipoProprietario(TipoProprietario.EMPRESA);
            pedidoFacade.salvarContrato(id, form);
            return HttpResponse.seeOther(URI.create("/pedidos/" + id + "?sucesso=contrato"));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return HttpResponse.seeOther(URI.create("/pedidos/" + id + "?erro=contrato"));
        }
    }

    @Post("/{id}/recusar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> recusar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                   @PathVariable Long id,
                                   @QueryValue Optional<String> status) {
        if (getEmpresa(clienteId).isEmpty()) return HttpResponse.seeOther(URI.create("/perfil"));
        try {
            pedidoFacade.recusarPedidoComoEmpresa(id);
            return HttpResponse.seeOther(URI.create(buildRedirect(status, "recusa", null)));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return HttpResponse.seeOther(URI.create(buildRedirect(status, null, "acao")));
        }
    }

    private Optional<Cliente> getEmpresa(Optional<Long> clienteId) {
        return clienteId.flatMap(authSessionFacade::getClienteAutenticado).filter(Cliente::isEmpresa);
    }

    private Optional<PedidoStatus> parseStatus(String status) {
        if (status == null || status.isBlank() || "TODOS".equalsIgnoreCase(status)) return Optional.empty();
        try { return Optional.of(PedidoStatus.valueOf(status)); } catch (IllegalArgumentException e) { return Optional.empty(); }
    }

    private String buildRedirect(Optional<String> status, String sucesso, String erro) {
        StringBuilder redirect = new StringBuilder("/empresa/pedidos");
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
}
