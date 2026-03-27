package com.vrumm.controller;

import com.vrumm.domain.Cliente;
import com.vrumm.dto.ClienteForm;
import com.vrumm.service.ClienteService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Tag(name = "clientes")
@Controller("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Lista os clientes")
    @Get
    @View("clientes/list")
    public Map<String, Object> listar() {
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Clientes");
        model.put("clientes", clienteService.listarTodos());
        return model;
    }

    @Operation(summary = "Abre o formulário de novo cliente")
    @Get("/novo")
    @View("clientes/form")
    public Map<String, Object> novo() {
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Novo Cliente");
        model.put("cliente", new ClienteForm());
        model.put("modoEdicao", false);
        return model;
    }

    @Operation(summary = "Salva um novo cliente")
    @Post
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> salvar(@Body @Valid ClienteForm form) {
        clienteService.salvar(form);
        return HttpResponse.seeOther(URI.create("/clientes"));
    }

    @Operation(summary = "Abre o formulário de edição de cliente")
    @Get("/{id}/editar")
    @View("clientes/form")
    public ModelAndView<Map<String, Object>> editar(@PathVariable Long id) {
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(id);

        if (clienteOpt.isEmpty()) {
            return new ModelAndView<>("clientes/form", Map.of(
                    "title", "Cliente não encontrado",
                    "cliente", new ClienteForm(),
                    "modoEdicao", false,
                    "erro", "Cliente não encontrado"
            ));
        }

        Cliente cliente = clienteOpt.get();
        ClienteForm form = new ClienteForm();
        form.setNome(cliente.getNome());
        form.setCpf(cliente.getCpf().getValor());
        form.setRg(cliente.getRg());
        form.setProfissao(cliente.getProfissao());

        return new ModelAndView<>("clientes/form", Map.of(
                "title", "Editar Cliente",
                "cliente", form,
                "modoEdicao", true,
                "clienteId", cliente.getId()
        ));
    }

    @Operation(summary = "Atualiza um cliente existente")
    @Post("/{id}/editar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> atualizar(@PathVariable Long id, @Body @Valid ClienteForm form) {
        clienteService.atualizar(id, form);
        return HttpResponse.seeOther(URI.create("/clientes"));
    }

    @Operation(summary = "Remove um cliente")
    @Post("/{id}/remover")
    public HttpResponse<?> remover(@PathVariable Long id) {
        clienteService.remover(id);
        return HttpResponse.seeOther(URI.create("/clientes"));
    }
}