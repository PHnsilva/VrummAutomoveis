package com.vrumm.domain.cliente.model;

import java.util.UUID;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable 
@Introspected
public class Cliente {
    private final UUID id;
    private String nome;
    private String email;


    public Cliente(String nome, String email) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
    }


    public Cliente(UUID id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }


    public void atualizarNome(String novoNome) {
        if (novoNome == null || novoNome.isBlank()) {
            throw new RuntimeException("Nome não pode ser vazio");
        }
        this.nome = novoNome;
    }

    public void atualizarEmail(String novoEmail) {
        if (novoEmail == null || !novoEmail.contains("@")) {
            throw new RuntimeException("Email inválido");
        }
        this.email = novoEmail;
    }
    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
}