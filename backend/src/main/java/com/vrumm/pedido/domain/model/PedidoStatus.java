package com.vrumm.pedido.domain.model;

public enum PedidoStatus {
    EM_ANALISE("Em análise", "status-badge status-badge--warning"),
    APROVADO("Aprovado", "status-badge status-badge--success"),
    REPROVADO("Reprovado", "status-badge status-badge--danger");

    private final String descricao;
    private final String badgeClass;

    PedidoStatus(String descricao, String badgeClass) {
        this.descricao = descricao;
        this.badgeClass = badgeClass;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getBadgeClass() {
        return badgeClass;
    }

    public static PedidoStatus statusInicial() {
        return EM_ANALISE;
    }
}
