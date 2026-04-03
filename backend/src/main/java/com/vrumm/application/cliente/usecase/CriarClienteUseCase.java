package com.vrumm.application.cliente.usecase;

import com.vrumm.domain.cliente.model.Cliente;
import com.vrumm.domain.cliente.repository.ClienteRepository;

import jakarta.inject.Singleton;

import java.util.UUID;

@Singleton
public class CriarClienteUseCase {

    private final ClienteRepository repository;

    public CriarClienteUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente executar(String nome, String email) {
        Cliente cliente = new Cliente(UUID.randomUUID(), nome, email);
        return repository.salvar(cliente);
    }
}