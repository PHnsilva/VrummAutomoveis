package com.vrumm.infrastructure.persistence.cliente;

import com.vrumm.domain.cliente.model.Cliente;
import com.vrumm.domain.cliente.repository.ClienteRepository;

import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteRepositoryMicronaut repository;

    public ClienteRepositoryImpl(ClienteRepositoryMicronaut repository) {
        this.repository = repository;
    }

@Override
public Cliente salvar(Cliente cliente) {
    ClienteEntity entity = toEntity(cliente);
    
    if (repository.existsById(cliente.getId())) {
        return toDomain(repository.update(entity));
    } else {
        return toDomain(repository.save(entity));
    }
}

    @Override
    public Optional<Cliente> buscarPorId(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Cliente> listarTodos() {
        return ((List<ClienteEntity>) repository.findAll())
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(UUID id) {
        repository.deleteById(id);
    }

    private ClienteEntity toEntity(Cliente cliente) {
        ClienteEntity entity = new ClienteEntity();
        entity.setId(cliente.getId());
        entity.setNome(cliente.getNome());
        entity.setEmail(cliente.getEmail());
        return entity;
    }

    private Cliente toDomain(ClienteEntity entity) {
        return new Cliente(entity.getId(), entity.getNome(), entity.getEmail());
    }
}