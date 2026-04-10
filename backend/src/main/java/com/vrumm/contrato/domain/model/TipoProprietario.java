package com.vrumm.contrato.domain.model;

public enum TipoProprietario {
    CLIENTE("Cliente"),
    EMPRESA("Empresa"),
    BANCO("Banco");

    private final String descricao;
    TipoProprietario(String descricao) { this.descricao = descricao; }
    public String getDescricao() { return descricao; }
}
