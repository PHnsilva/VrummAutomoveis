package com.vrumm.agente.domain.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

@Introspected
@MappedEntity("empresa")
public class Empresa {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;

    public Empresa() {}

    public Empresa(Long id, String nome) { this.id = id; setNome(nome); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome da empresa é obrigatório");
        String v = nome.trim();
        if (v.length() > 120) throw new IllegalArgumentException("Nome da empresa deve ter no máximo 120 caracteres");
        this.nome = v;
    }
}
