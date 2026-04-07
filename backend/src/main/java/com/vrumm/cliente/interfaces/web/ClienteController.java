package com.vrumm.cliente.interfaces.web;

import com.vrumm.auth.application.facade.AuthSessionFacade;
import com.vrumm.cliente.application.dto.ClienteForm;
import com.vrumm.cliente.application.facade.ClienteFacade;
import com.vrumm.cliente.domain.model.Cliente;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.CookieValue;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.views.ModelAndView;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;

import java.net.URI;
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
    public HttpResponse<?> listar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }

        Map<String, Object> model = new HashMap<>();
        model.put("title", "Clientes");
        model.put("cliente", autenticado.get());
        model.put("clientes", clienteFacade.listarTodos());
        return HttpResponse.ok(new ModelAndView<>("clientes/list", model));
    }

    @Get("/novo")
    public HttpResponse<?> novo(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }

        Map<String, Object> model = new HashMap<>();
        model.put("title", "Novo Cliente");
        model.put("cliente", autenticado.get());
        model.put("form", new ClienteForm());
        model.put("modoEdicao", false);
        return HttpResponse.ok(new ModelAndView<>("clientes/form", model));
    }

    @Post
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> salvar(@Body @Valid ClienteForm form) {
        clienteFacade.salvar(form);
        return HttpResponse.seeOther(URI.create("/clientes"));
    }

    @Get("/{id}/editar")
    public HttpResponse<?> editar(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                  @PathVariable Long id) {
        Optional<Cliente> autenticado = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }

        Optional<Cliente> clienteOpt = clienteFacade.buscarPorId(id);
        if (clienteOpt.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/clientes"));
        }

        Cliente clienteEdicao = clienteOpt.get();
        ClienteForm form = ClienteForm.fromCliente(clienteEdicao);
        form.setSenha("");

        Map<String, Object> model = new HashMap<>();
        model.put("title", "Editar Cliente");
        model.put("cliente", autenticado.get());
        model.put("form", form);
        model.put("modoEdicao", true);
        model.put("clienteId", clienteEdicao.getId());
        return HttpResponse.ok(new ModelAndView<>("clientes/form", model));
    }

    @Post("/{id}/editar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> atualizar(@PathVariable Long id, @Body @Valid ClienteForm form) {
        clienteFacade.atualizar(id, form);
        return HttpResponse.seeOther(URI.create("/clientes"));
    }

    @Post("/{id}/remover")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> remover(@PathVariable Long id) {
        clienteFacade.remover(id);
        return HttpResponse.seeOther(URI.create("/clientes"));
    }
}
