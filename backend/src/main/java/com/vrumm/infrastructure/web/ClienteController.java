package com.vrumm.infrastructure.web;

import com.vrumm.application.cliente.dto.ClienteForm;
import com.vrumm.application.cliente.usecase.*;
import com.vrumm.domain.cliente.model.Cliente;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.views.View;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller("/clientes")
public class ClienteController {

    private final CriarClienteUseCase criar;
    private final ListarClientesUseCase listar;
    private final BuscarClienteUseCase buscar;
    private final AtualizarClienteUseCase atualizar;
    private final DeletarClienteUseCase deletar;

    public ClienteController(
            CriarClienteUseCase criar,
            ListarClientesUseCase listar,
            BuscarClienteUseCase buscar,
            AtualizarClienteUseCase atualizar,
            DeletarClienteUseCase deletar) {
        this.criar = criar;
        this.listar = listar;
        this.buscar = buscar;
        this.atualizar = atualizar;
        this.deletar = deletar;
    }

    // LISTAR
    @Get
    @View("clientes/list")
    public Map<String, Object> listar() {
        return Map.of("clientes", listar.executar());
    }

    // FORM CREATE
    @Get("/novo")
    @View("clientes/form")
    public Map<String, Object> novo() {
        Map<String, Object> model = new HashMap<>();
        model.put("cliente", new ClienteForm());
        model.put("id", null);
        return model;
    }

    // CREATE
    @Post
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> salvar(@Body ClienteForm form) {
        criar.executar(form.getNome(), form.getEmail());
        return HttpResponse.seeOther(URI.create("/clientes"));
    }

    // FORM EDIT
    @Get("/{id}/editar")
    @View("clientes/form")
    public Map<String, Object> editar(UUID id) {
        Cliente cliente = buscar.executar(id);

        ClienteForm form = new ClienteForm();
        form.setNome(cliente.getNome());
        form.setEmail(cliente.getEmail());

        Map<String, Object> model = new HashMap<>();
        model.put("cliente", form);
        model.put("id", cliente.getId());

        return model;
    }

    // UPDATE
    @Post("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> atualizar(UUID id, @Body ClienteForm form) {
        atualizar.executar(id, form.getNome(), form.getEmail());
        return HttpResponse.seeOther(URI.create("/clientes"));
    }

    // DELETE
    @Post("/{id}/delete")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> deletar(UUID id) {
        deletar.executar(id);
        return HttpResponse.seeOther(URI.create("/clientes"));
    }
}