package com.vrumm.cliente.interfaces.api;

import com.vrumm.cliente.application.dto.ClienteForm;
import com.vrumm.cliente.application.dto.ClienteResponse;
import com.vrumm.cliente.application.facade.ClienteFacade;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Tag(name = "clientes")
@Controller("/api/clientes")
public class ClienteApiController {

    private final ClienteFacade clienteFacade;

    public ClienteApiController(ClienteFacade clienteFacade) {
        this.clienteFacade = clienteFacade;
    }

    @Operation(summary = "Lista todos os clientes")
    @Get
    public List<ClienteResponse> listar() {
        return clienteFacade.listarTodos().stream().map(ClienteResponse::fromCliente).toList();
    }

    @Operation(summary = "Busca um cliente por id")
    @Get("/{id}")
    public HttpResponse<ClienteResponse> buscarPorId(@PathVariable Long id) {
        Optional<ClienteResponse> response = clienteFacade.buscarPorId(id).map(ClienteResponse::fromCliente);
        return response.map(HttpResponse::ok).orElseGet(HttpResponse::notFound);
    }

    @Operation(summary = "Cria um novo cliente")
    @Post
    public HttpResponse<ClienteResponse> criar(@Body @Valid ClienteForm form) {
        return HttpResponse.created(ClienteResponse.fromCliente(clienteFacade.salvar(form)));
    }

    @Operation(summary = "Atualiza um cliente existente")
    @Put("/{id}")
    public HttpResponse<ClienteResponse> atualizar(@PathVariable Long id, @Body @Valid ClienteForm form) {
        return HttpResponse.ok(ClienteResponse.fromCliente(clienteFacade.atualizar(id, form)));
    }

    @Operation(summary = "Remove um cliente")
    @Delete("/{id}")
    public HttpResponse<Void> remover(@PathVariable Long id) {
        clienteFacade.remover(id);
        return HttpResponse.noContent();
    }
}
