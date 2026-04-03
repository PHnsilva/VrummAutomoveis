package com.vrumm.domain.cliente.repository;

import com.vrumm.domain.cliente.model.Cliente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository {

    Cliente salvar(Cliente cliente);

    Optional<Cliente> buscarPorId(UUID id);

    List<Cliente> listarTodos();

    void deletar(UUID id);
}