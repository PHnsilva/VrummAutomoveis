package com.vrumm.application.cliente.usecase;

import java.util.UUID;

import com.vrumm.domain.cliente.model.Cliente;
import com.vrumm.domain.cliente.repository.ClienteRepository;

import jakarta.inject.Singleton;

@Singleton
public class AtualizarClienteUseCase {

    private final ClienteRepository repository;

    public AtualizarClienteUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente executar(UUID id, String nome, String email) {
        Cliente cliente = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.atualizarNome(nome);
        cliente.atualizarEmail(email);

        return repository.salvar(cliente);
    }
}