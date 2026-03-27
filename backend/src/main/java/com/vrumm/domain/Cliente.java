package com.vrumm.domain;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Transient;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Introspected
@MappedEntity("cliente")
public class Cliente {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String nome;

    @TypeDef(type = DataType.STRING)
    private Cpf cpf;

    @NotBlank
    @Size(max = 20)
    private String rg;

    @NotBlank
    @Size(max = 100)
    private String profissao;

    @Transient
    private List<Rendimento> rendimentos = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(Long id, String nome, Cpf cpf, String rg, String profissao) {
        this.id = id;
        setNome(nome);
        setCpf(cpf);
        setRg(rg);
        setProfissao(profissao);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório");
        }

        String nomeTratado = nome.trim();

        if (nomeTratado.length() > 150) {
            throw new IllegalArgumentException("Nome do cliente deve ter no máximo 150 caracteres");
        }

        this.nome = nomeTratado;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public void setCpf(Cpf cpf) {
        if (cpf == null) {
            throw new IllegalArgumentException("CPF do cliente é obrigatório");
        }

        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        if (rg == null || rg.isBlank()) {
            throw new IllegalArgumentException("RG do cliente é obrigatório");
        }

        String rgTratado = rg.trim();

        if (rgTratado.length() > 20) {
            throw new IllegalArgumentException("RG do cliente deve ter no máximo 20 caracteres");
        }

        this.rg = rgTratado;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        if (profissao == null || profissao.isBlank()) {
            throw new IllegalArgumentException("Profissão do cliente é obrigatória");
        }

        String profissaoTratada = profissao.trim();

        if (profissaoTratada.length() > 100) {
            throw new IllegalArgumentException("Profissão do cliente deve ter no máximo 100 caracteres");
        }

        this.profissao = profissaoTratada;
    }

    public List<Rendimento> getRendimentos() {
        return Collections.unmodifiableList(rendimentos);
    }

    public void adicionarRendimento(Rendimento rendimento) {
        if (rendimento == null) {
            throw new IllegalArgumentException("Rendimento é obrigatório");
        }

        if (rendimentos.size() >= 3) {
            throw new IllegalStateException("Cliente pode possuir no máximo 3 rendimentos");
        }

        rendimentos.add(rendimento);
    }

    public void removerRendimento(Rendimento rendimento) {
        if (rendimento == null) {
            throw new IllegalArgumentException("Rendimento é obrigatório");
        }

        rendimentos.remove(rendimento);
    }

    public boolean podeSerRemovido(boolean possuiVinculosAtivos) {
        return !possuiVinculosAtivos;
    }
}