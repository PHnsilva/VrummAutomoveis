package com.vrumm.cliente.domain.model;

public enum PerfilAcesso {

    CLIENTE("Cliente"),
    ADMIN("Administrador");

    private final String descricao;

    PerfilAcesso(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
