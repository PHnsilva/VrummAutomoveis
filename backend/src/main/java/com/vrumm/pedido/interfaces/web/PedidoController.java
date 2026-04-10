package com.vrumm.pedido.interfaces.web;

import com.vrumm.auth.application.facade.AuthSessionFacade;
import com.vrumm.automovel.domain.model.Automovel;
import com.vrumm.cliente.domain.model.Cliente;
import com.vrumm.contrato.domain.model.TipoContrato;
import com.vrumm.contrato.domain.model.TipoProprietario;
import com.vrumm.pedido.application.dto.ContratoCreditoForm;
import com.vrumm.pedido.application.dto.ContratoForm;
import com.vrumm.pedido.application.dto.FinanceiroAvaliacaoForm;
import com.vrumm.pedido.application.dto.PagamentoPedidoForm;
import com.vrumm.pedido.application.dto.PedidoForm;
import com.vrumm.pedido.application.dto.PedidoStatusDto;
import com.vrumm.pedido.application.facade.PedidoFacade;
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
    public HttpResponse<?> listar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                  @QueryValue Optional<String> sucesso) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }
        if (autenticado.get().isEmpresa()) {
            return HttpResponse.seeOther(URI.create("/empresa/pedidos"));
        }
        if (autenticado.get().isBanco()) {
            return HttpResponse.seeOther(URI.create("/banco/pedidos"));
        }

        Map<String, Object> model = new HashMap<>();
        model.put("title", "Meus Pedidos");
        model.put("cliente", autenticado.get());
        model.put("pedidos", pedidoFacade.listarPedidosDoCliente(autenticado.get().getId()));
        model.put("sucesso", sucesso.filter("pedido-criado"::equals).isPresent()
                ? "Pedido de aluguel criado com sucesso."
                : sucesso.filter("cancelado"::equals).isPresent() ? "Pedido cancelado com sucesso." : null);
        return HttpResponse.ok(new ModelAndView<>("pedidos/list", model));
    }

    @Get("/novo")
    public HttpResponse<?> novo(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }
        return renderFormulario(autenticado.get(), new PedidoForm(), null, false, null);
    }

    @Get("/{id}/editar")
    public HttpResponse<?> editar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                  @PathVariable Long id,
                                  @QueryValue Optional<String> erro) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }
        Optional<PedidoForm> formOpt = autenticado.get().isCliente()
                ? pedidoFacade.buscarFormularioPedidoDoCliente(autenticado.get().getId(), id)
                : pedidoFacade.buscarFormularioPedido(id);
        if (formOpt.isEmpty()) {
            return HttpResponse.seeOther(URI.create(redirecionarBase(autenticado.get())));
        }
        return renderFormulario(autenticado.get(), formOpt.get(), erro.orElse(null), true, id);
    }

    @Get("/{id}")
    public HttpResponse<?> detalhar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                    @PathVariable Long id,
                                    @QueryValue Optional<String> sucesso,
                                    @QueryValue Optional<String> erro) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }

        Optional<PedidoStatusDto> pedidoOpt = autenticado.get().isCliente()
                ? pedidoFacade.buscarResumoPedidoDoCliente(autenticado.get().getId(), id)
                : pedidoFacade.buscarResumoPedido(id);
        if (pedidoOpt.isEmpty()) {
            return HttpResponse.seeOther(URI.create(redirecionarBase(autenticado.get())));
        }

        Map<String, Object> model = new HashMap<>();
        model.put("title", "Status do Pedido");
        model.put("cliente", autenticado.get());
        model.put("pedido", pedidoOpt.get());
        model.put("pagamentoForm", new PagamentoPedidoForm());
        model.put("avaliacaoForm", new FinanceiroAvaliacaoForm());
        model.put("contratoForm", pedidoFacade.buscarContratoForm(id).orElse(new ContratoForm()));
        model.put("contratoCreditoForm", pedidoFacade.buscarContratoCreditoForm(id).orElse(new ContratoCreditoForm()));
        model.put("tiposContrato", TipoContrato.values());
        model.put("tiposProprietario", TipoProprietario.values());
        model.put("sucesso", mapearSucesso(sucesso));
        model.put("erro", mapearErro(erro));
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
            pedidoFacade.criarPedido(autenticado.get().getId(), form);
            return HttpResponse.seeOther(URI.create(redirecionarBase(autenticado.get()) + "?sucesso=pedido-criado"));
        } catch (IllegalArgumentException e) {
            return renderFormulario(autenticado.get(), form, e.getMessage(), false, null);
        }
    }

    @Post("/{id}/editar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> atualizar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                     @PathVariable Long id,
                                     @Body @Valid PedidoForm form) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }
        try {
            if (autenticado.get().isEmpresa()) {
                pedidoFacade.atualizarPedidoComoEmpresa(id, form);
            } else {
                pedidoFacade.atualizarPedidoDoCliente(autenticado.get().getId(), id, form);
            }
            return HttpResponse.seeOther(URI.create("/pedidos/" + id + "?sucesso=edicao"));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return renderFormulario(autenticado.get(), form, e.getMessage(), true, id);
        }
    }

    @Post("/{id}/cancelar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> cancelar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                    @PathVariable Long id) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }
        try {
            if (autenticado.get().isEmpresa()) {
                pedidoFacade.cancelarPedidoComoEmpresa(id);
            } else {
                pedidoFacade.cancelarPedidoDoCliente(autenticado.get().getId(), id);
            }
            return HttpResponse.seeOther(URI.create(redirecionarBase(autenticado.get()) + "?sucesso=cancelado"));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return HttpResponse.seeOther(URI.create("/pedidos/" + id + "?erro=cancelamento"));
        }
    }

    @Post("/{id}/confirmar-pagamento")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> confirmarPagamento(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                              @PathVariable Long id,
                                              @Body @Valid PagamentoPedidoForm form) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }

        try {
            if (autenticado.get().isBanco()) {
                pedidoFacade.confirmarPagamentoComoBanco(id, form.getValorPago());
            } else {
                pedidoFacade.confirmarPagamentoDoCliente(autenticado.get().getId(), id, form.getValorPago());
            }
            return HttpResponse.seeOther(URI.create("/pedidos/" + id + "?sucesso=pagamento"));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return HttpResponse.seeOther(URI.create("/pedidos/" + id + "?erro=pagamento"));
        }
    }

    private HttpResponse<ModelAndView<Map<String, Object>>> renderFormulario(Cliente cliente,
                                                                              PedidoForm form,
                                                                              String erro,
                                                                              boolean edicao,
                                                                              Long pedidoId) {
        List<Automovel> automoveis = pedidoFacade.listarAutomoveisDisponiveis();
        Map<String, Object> model = new HashMap<>();
        model.put("title", edicao ? "Editar Pedido" : "Novo Pedido");
        model.put("cliente", cliente);
        model.put("form", form);
        model.put("automoveis", automoveis);
        model.put("erro", erro);
        model.put("edicao", edicao);
        model.put("pedidoId", pedidoId);
        return HttpResponse.ok(new ModelAndView<>("pedidos/form", model));
    }

    private String redirecionarBase(Cliente cliente) {
        if (cliente.isEmpresa()) return "/empresa/pedidos";
        if (cliente.isBanco()) return "/banco/pedidos";
        return "/pedidos";
    }

    private String mapearSucesso(Optional<String> sucesso) {
        if (sucesso.filter("pagamento"::equals).isPresent()) return "Pagamento confirmado com sucesso.";
        if (sucesso.filter("edicao"::equals).isPresent()) return "Pedido atualizado com sucesso.";
        if (sucesso.filter("contrato"::equals).isPresent()) return "Contrato operacional registrado com sucesso.";
        if (sucesso.filter("credito"::equals).isPresent()) return "Contrato de crédito registrado com sucesso.";
        if (sucesso.filter("avaliacao"::equals).isPresent()) return "Parecer financeiro registrado com sucesso.";
        return null;
    }

    private String mapearErro(Optional<String> erro) {
        if (erro.filter("pagamento"::equals).isPresent()) return "Não foi possível confirmar o pagamento deste pedido.";
        if (erro.filter("cancelamento"::equals).isPresent()) return "Não foi possível cancelar este pedido.";
        if (erro.filter("contrato"::equals).isPresent()) return "Não foi possível registrar o contrato deste pedido.";
        if (erro.filter("credito"::equals).isPresent()) return "Não foi possível registrar o contrato de crédito deste pedido.";
        if (erro.filter("avaliacao"::equals).isPresent()) return "Não foi possível registrar o parecer financeiro deste pedido.";
        return null;
    }
}
