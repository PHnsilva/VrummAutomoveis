package com.vrumm.infrastructure.persistence.cliente;

import io.micronaut.data.annotation.*;

import java.util.UUID;

@MappedEntity("clientes")
public class ClienteEntity {

    @Id
    private UUID id;

    private String nome;
    private String email;

    // getters/setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}