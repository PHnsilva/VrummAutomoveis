package com.vrumm.agente.domain.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

@Introspected
@MappedEntity("banco")
public class Banco {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String codigo;

    public Banco() {}

    public Banco(Long id, String nome, String codigo) {
        this.id = id;
        setNome(nome);
        setCodigo(codigo);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome do banco é obrigatório");
        String v = nome.trim();
        if (v.length() > 120) throw new IllegalArgumentException("Nome do banco deve ter no máximo 120 caracteres");
        this.nome = v;
    }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) throw new IllegalArgumentException("Código do banco é obrigatório");
        String v = codigo.trim();
        if (v.length() > 20) throw new IllegalArgumentException("Código do banco deve ter no máximo 20 caracteres");
        this.codigo = v;
    }
}
