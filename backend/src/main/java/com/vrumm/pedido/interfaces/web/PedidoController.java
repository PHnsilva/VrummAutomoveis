package com.vrumm.pedido.interfaces.web;

import com.vrumm.auth.application.facade.AuthSessionFacade;
import com.vrumm.automovel.domain.model.Automovel;
import com.vrumm.cliente.domain.model.Cliente;
import com.vrumm.pedido.application.dto.PedidoForm;
import com.vrumm.pedido.application.dto.PedidoStatusDto;
import com.vrumm.pedido.application.facade.PedidoFacade;
import com.vrumm.pedido.domain.model.PedidoAluguel;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Hidden
@Controller("/pedidos")
public class PedidoController {

    private final PedidoFacade pedidoFacade;
    private final AuthSessionFacade authSessionFacade;

    public PedidoController(PedidoFacade pedidoFacade, AuthSessionFacade authSessionFacade) {
        this.pedidoFacade = pedidoFacade;
        this.authSessionFacade = authSessionFacade;
    }

    @Get
    public HttpResponse<?> listar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }

        Map<String, Object> model = new HashMap<>();
        model.put("title", "Meus Pedidos");
        model.put("cliente", autenticado.get());
        model.put("pedidos", pedidoFacade.listarPedidosDoCliente(autenticado.get().getId()));
        return HttpResponse.ok(new ModelAndView<>("pedidos/list", model));
    }

    @Get("/novo")
    public HttpResponse<?> novo(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }
        return renderFormulario(autenticado.get(), new PedidoForm(), null);
    }

    @Get("/{id}")
    public HttpResponse<?> detalhar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                    @PathVariable Long id,
                                    @QueryValue Optional<String> sucesso) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }

        Optional<PedidoStatusDto> pedidoOpt = pedidoFacade.buscarResumoPedidoDoCliente(autenticado.get().getId(), id);
        if (pedidoOpt.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/pedidos"));
        }

        Map<String, Object> model = new HashMap<>();
        model.put("title", "Status do Pedido");
        model.put("cliente", autenticado.get());
        model.put("pedido", pedidoOpt.get());
        model.put("sucesso", sucesso.isPresent() ? "Pedido de aluguel criado com sucesso." : null);
        return HttpResponse.ok(new ModelAndView<>("pedidos/detail", model));
    }

    @Post
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> salvar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                  @Body @Valid PedidoForm form) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }

        try {
            PedidoAluguel pedido = pedidoFacade.criarPedido(autenticado.get().getId(), form);
            return HttpResponse.seeOther(URI.create("/pedidos/" + pedido.getId() + "?sucesso=1"));
        } catch (IllegalArgumentException e) {
            return renderFormulario(autenticado.get(), form, e.getMessage());
        }
    }

    private HttpResponse<ModelAndView<Map<String, Object>>> renderFormulario(Cliente cliente,
                                                                              PedidoForm form,
                                                                              String erro) {
        List<Automovel> automoveis = pedidoFacade.listarAutomoveisDisponiveis();
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Novo Pedido");
        model.put("cliente", cliente);
        model.put("form", form);
        model.put("automoveis", automoveis);
        model.put("erro", erro);
        return HttpResponse.ok(new ModelAndView<>("pedidos/form", model));
    }
}
