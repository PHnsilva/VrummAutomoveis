package com.vrumm.application.cliente.usecase;

import java.util.UUID;

import com.vrumm.domain.cliente.model.Cliente;
import com.vrumm.domain.cliente.repository.ClienteRepository;

import jakarta.inject.Singleton;

@Singleton
public class BuscarClienteUseCase {

    private final ClienteRepository repository;

    public BuscarClienteUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente executar(UUID id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }
}