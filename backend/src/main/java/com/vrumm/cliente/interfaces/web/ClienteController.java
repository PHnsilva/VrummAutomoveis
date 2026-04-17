package com.vrumm.cliente.interfaces.web;

import com.vrumm.auth.application.facade.AuthSessionFacade;
import com.vrumm.cliente.application.dto.ClienteForm;
import com.vrumm.cliente.application.facade.ClienteFacade;
import com.vrumm.cliente.domain.model.Cliente;
import com.vrumm.shared.exception.DuplicateResourceException;
import com.vrumm.shared.exception.OperationNotAllowedException;
import com.vrumm.shared.exception.ResourceNotFoundException;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Hidden
@Controller("/clientes")
public class ClienteController {
    private final ClienteFacade clienteFacade;
    private final AuthSessionFacade authSessionFacade;

    public ClienteController(ClienteFacade clienteFacade, AuthSessionFacade authSessionFacade) {
        this.clienteFacade = clienteFacade;
        this.authSessionFacade = authSessionFacade;
    }

    @Get
    public HttpResponse<?> listar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                  @QueryValue Optional<String> erro,
                                  @QueryValue Optional<String> sucesso) {
        Optional<Cliente> autenticado = getEmpresa(clienteId);
        if (autenticado.isEmpty()) return HttpResponse.seeOther(URI.create("/perfil"));
        return renderList(autenticado.get(), erro.orElse(null), sucesso.orElse(null));
    }

    @Get("/novo")
    public HttpResponse<?> novo(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                @QueryValue Optional<String> erro,
                                @QueryValue Optional<String> sucesso) {
        Optional<Cliente> autenticado = getEmpresa(clienteId);
        if (autenticado.isEmpty()) return HttpResponse.seeOther(URI.create("/perfil"));
        return renderForm(autenticado.get(), new ClienteForm(), false, null, erro.orElse(null), sucesso.orElse(null));
    }

    @Post
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> salvar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                  @Body @Valid ClienteForm form) {
        Optional<Cliente> autenticado = getEmpresa(clienteId);
        if (autenticado.isEmpty()) return HttpResponse.seeOther(URI.create("/perfil"));
        try {
            clienteFacade.salvar(form);
            return HttpResponse.seeOther(URI.create("/clientes?sucesso=" + encode("Cliente cadastrado com sucesso")));
        } catch (DuplicateResourceException | IllegalArgumentException e) {
            return renderForm(autenticado.get(), form, false, null, e.getMessage(), null);
        }
    }

    @Get("/{id}/editar")
    public HttpResponse<?> editar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                  @PathVariable Long id,
                                  @QueryValue Optional<String> erro,
                                  @QueryValue Optional<String> sucesso) {
        Optional<Cliente> autenticado = getEmpresa(clienteId);
        if (autenticado.isEmpty()) return HttpResponse.seeOther(URI.create("/perfil"));
        return clienteFacade.buscarClienteGerenciavel(id)
                .<HttpResponse<?>>map(cliente -> renderForm(autenticado.get(), ClienteForm.fromCliente(cliente), true, cliente.getId(), erro.orElse(null), sucesso.orElse(null)))
                .orElseGet(() -> HttpResponse.seeOther(URI.create("/clientes?erro=" + encode("Cliente não encontrado"))));
    }

    @Post("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> atualizar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                     @PathVariable Long id,
                                     @Body @Valid ClienteForm form) {
        Optional<Cliente> autenticado = getEmpresa(clienteId);
        if (autenticado.isEmpty()) return HttpResponse.seeOther(URI.create("/perfil"));
        try {
            clienteFacade.atualizar(id, form);
            return HttpResponse.seeOther(URI.create("/clientes?sucesso=" + encode("Cliente atualizado com sucesso")));
        } catch (DuplicateResourceException | IllegalArgumentException e) {
            return renderForm(autenticado.get(), form, true, id, e.getMessage(), null);
        } catch (ResourceNotFoundException e) {
            return HttpResponse.seeOther(URI.create("/clientes?erro=" + encode(e.getMessage())));
        }
    }

    @Post("/{id}/remover")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> remover(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                   @PathVariable Long id) {
        Optional<Cliente> autenticado = getEmpresa(clienteId);
        if (autenticado.isEmpty()) return HttpResponse.seeOther(URI.create("/perfil"));
        try {
            clienteFacade.remover(id);
            return HttpResponse.seeOther(URI.create("/clientes?sucesso=" + encode("Cliente removido com sucesso")));
        } catch (ResourceNotFoundException | OperationNotAllowedException e) {
            return HttpResponse.seeOther(URI.create("/clientes?erro=" + encode(e.getMessage())));
        }
    }

    private Optional<Cliente> getEmpresa(Optional<Long> clienteId) {
        return clienteId.flatMap(authSessionFacade::getClienteAutenticado).filter(Cliente::isEmpresa);
    }

    private HttpResponse<ModelAndView<Map<String, Object>>> renderList(Cliente autenticado, String erro, String sucesso) {
        Map<String, Object> model = new HashMap<>();
        model.put("cliente", autenticado);
        model.put("clientes", clienteFacade.listarClientesGerenciaveis());
        model.put("erro", erro);
        model.put("sucesso", sucesso);
        model.put("title", "Gerenciar usuários");
        return HttpResponse.ok(new ModelAndView<>("clientes/list", model));
    }

    private HttpResponse<ModelAndView<Map<String, Object>>> renderForm(Cliente autenticado, ClienteForm form, boolean edicao, Long clienteEdicaoId, String erro, String sucesso) {
        Map<String, Object> model = new HashMap<>();
        model.put("cliente", autenticado);
        model.put("form", form);
        model.put("edicao", edicao);
        model.put("clienteEdicaoId", clienteEdicaoId);
        model.put("erro", erro);
        model.put("sucesso", sucesso);
        model.put("title", edicao ? "Editar cliente" : "Novo cliente");
        return HttpResponse.ok(new ModelAndView<>("clientes/form", model));
    }

    private String encode(String value) { return URLEncoder.encode(value, StandardCharsets.UTF_8); }
}
