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
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }
        return renderList(autenticado.get(), erro.orElse(null), sucesso.orElse(null));
    }

    @Get("/novo")
    public HttpResponse<?> novo(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                @QueryValue Optional<String> erro,
                                @QueryValue Optional<String> sucesso) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }
        return renderForm(autenticado.get(), new ClienteForm(), false, null, erro.orElse(null), sucesso.orElse(null));
    }

    @Post
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> salvar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                  @Body @Valid ClienteForm form) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }
        try {
            clienteFacade.salvar(form);
            return redirectComMensagem("/clientes", "Cliente cadastrado com sucesso", false);
        } catch (DuplicateResourceException | IllegalArgumentException | IllegalStateException exception) {
            return renderForm(autenticado.get(), form, false, null, exception.getMessage(), null);
        }
    }

    @Get("/{id}/editar")
    public HttpResponse<?> editar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                  @PathVariable Long id,
                                  @QueryValue Optional<String> erro,
                                  @QueryValue Optional<String> sucesso) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }

        Optional<Cliente> clienteOpt = clienteFacade.buscarPorId(id);
        if (clienteOpt.isEmpty()) {
            return redirectComMensagem("/clientes", "Cliente não encontrado", true);
        }

        Cliente clienteEdicao = clienteOpt.get();
        ClienteForm form = ClienteForm.fromCliente(clienteEdicao);
        form.setSenha("");
        return renderForm(autenticado.get(), form, true, clienteEdicao.getId(), erro.orElse(null), sucesso.orElse(null));
    }

    @Post("/{id}/editar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> atualizar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                     @PathVariable Long id,
                                     @Body @Valid ClienteForm form) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }
        try {
            clienteFacade.atualizar(id, form);
            return redirectComMensagem("/clientes", "Cliente atualizado com sucesso", false);
        } catch (ResourceNotFoundException exception) {
            return redirectComMensagem("/clientes", exception.getMessage(), true);
        } catch (DuplicateResourceException | IllegalArgumentException | IllegalStateException exception) {
            return renderForm(autenticado.get(), form, true, id, exception.getMessage(), null);
        }
    }

    @Post("/{id}/remover")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> remover(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                   @PathVariable Long id) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }
        try {
            clienteFacade.remover(id);
            return redirectComMensagem("/clientes", "Cliente removido com sucesso", false);
        } catch (ResourceNotFoundException | OperationNotAllowedException | IllegalArgumentException | IllegalStateException exception) {
            return renderList(autenticado.get(), exception.getMessage(), null);
        }
    }

    private HttpResponse<?> renderList(Cliente autenticado, String erro, String sucesso) {
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Clientes");
        model.put("cliente", autenticado);
        model.put("clientes", clienteFacade.listarTodos());
        model.put("erro", erro);
        model.put("sucesso", sucesso);
        return HttpResponse.ok(new ModelAndView<>("clientes/list", model));
    }

    private HttpResponse<?> renderForm(Cliente autenticado,
                                       ClienteForm form,
                                       boolean modoEdicao,
                                       Long clienteId,
                                       String erro,
                                       String sucesso) {
        Map<String, Object> model = new HashMap<>();
        model.put("title", modoEdicao ? "Editar Cliente" : "Novo Cliente");
        model.put("cliente", autenticado);
        model.put("form", form);
        model.put("modoEdicao", modoEdicao);
        model.put("clienteId", clienteId);
        model.put("erro", erro);
        model.put("sucesso", sucesso);
        return HttpResponse.ok(new ModelAndView<>("clientes/form", model));
    }

    private HttpResponse<?> redirectComMensagem(String path, String mensagem, boolean erro) {
        String parametro = erro ? "erro" : "sucesso";
        String encoded = URLEncoder.encode(mensagem, StandardCharsets.UTF_8);
        return HttpResponse.seeOther(URI.create(path + "?" + parametro + "=" + encoded));
    }
}
