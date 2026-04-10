package com.vrumm.contrato.domain.model;

public enum TipoContrato {
    ALUGUEL_PADRAO("Aluguel padrão"),
    ALUGUEL_COM_CREDITO("Aluguel com contrato de crédito");

    private final String descricao;
    TipoContrato(String descricao) { this.descricao = descricao; }
    public String getDescricao() { return descricao; }
}
