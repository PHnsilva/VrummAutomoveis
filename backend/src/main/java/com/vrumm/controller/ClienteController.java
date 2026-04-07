package com.vrumm.controller;

import com.vrumm.domain.Cliente;
import com.vrumm.dto.ClienteForm;
import com.vrumm.service.ClienteService;
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

    private final ClienteService clienteService;
    private final AuthController authController;

    public ClienteController(ClienteService clienteService, AuthController authController) {
        this.clienteService = clienteService;
        this.authController = authController;
    }

    @Get
    public HttpResponse<?> listar(@CookieValue(value = AuthController.AUTH_COOKIE) Optional<Long> clienteId) {
        Optional<Cliente> autenticado = clienteId.flatMap(authController::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }

        Map<String, Object> model = new HashMap<>();
        model.put("title", "Clientes");
        model.put("cliente", autenticado.get());
        model.put("clientes", clienteService.listarTodos());
        return HttpResponse.ok(new ModelAndView<>("clientes/list", model));
    }

    @Get("/novo")
    public HttpResponse<?> novo(@CookieValue(value = AuthController.AUTH_COOKIE) Optional<Long> clienteId) {
        Optional<Cliente> autenticado = clienteId.flatMap(authController::getClienteAutenticado);
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
        clienteService.salvar(form);
        return HttpResponse.seeOther(URI.create("/clientes"));
    }

    @Get("/{id}/editar")
    public HttpResponse<?> editar(@CookieValue(value = AuthController.AUTH_COOKIE) Optional<Long> clienteId,
                                  @PathVariable Long id) {
        Optional<Cliente> autenticado = clienteId.flatMap(authController::getClienteAutenticado);
        if (autenticado.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }

        Optional<Cliente> clienteOpt = clienteService.buscarPorId(id);
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
        clienteService.atualizar(id, form);
        return HttpResponse.seeOther(URI.create("/clientes"));
    }

    @Post("/{id}/remover")
    public HttpResponse<?> remover(@PathVariable Long id) {
        clienteService.remover(id);
        return HttpResponse.seeOther(URI.create("/clientes"));
    }
}
