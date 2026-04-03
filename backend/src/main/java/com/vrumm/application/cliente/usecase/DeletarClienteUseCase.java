package com.vrumm.application.cliente.usecase;

import java.util.UUID;

import com.vrumm.domain.cliente.repository.ClienteRepository;

import jakarta.inject.Singleton;

@Singleton
public class DeletarClienteUseCase {

    private final ClienteRepository repository;

    public DeletarClienteUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public void executar(UUID id) {
        repository.deletar(id);
    }
}