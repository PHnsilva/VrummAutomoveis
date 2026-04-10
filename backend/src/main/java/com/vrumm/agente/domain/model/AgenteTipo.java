package com.vrumm.agente.domain.model;

public enum AgenteTipo {
    EMPRESA("Empresa"),
    BANCO("Banco");

    private final String descricao;

    AgenteTipo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
