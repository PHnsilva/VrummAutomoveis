package com.vrumm.application.cliente.usecase;

import java.util.List;

import com.vrumm.domain.cliente.model.Cliente;
import com.vrumm.domain.cliente.repository.ClienteRepository;

import jakarta.inject.Singleton;

@Singleton
public class ListarClientesUseCase {

    private final ClienteRepository repository;

    public ListarClientesUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> executar() {
        return repository.listarTodos();
    }
}
