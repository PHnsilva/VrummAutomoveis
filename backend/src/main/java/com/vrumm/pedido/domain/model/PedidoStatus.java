package com.vrumm.pedido.domain.model;

public enum PedidoStatus {
    AGUARDANDO_ANALISE_FINANCEIRA("Aguardando análise financeira", "status-badge status-badge--warning"),
    AGUARDANDO_PAGAMENTO("Aguardando pagamento", "status-badge status-badge--info"),
    FINALIZADO("Finalizado", "status-badge status-badge--success"),
    RECUSADO("Recusado", "status-badge status-badge--danger"),
    CANCELADO("Cancelado", "status-badge status-badge--neutral");

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
        return AGUARDANDO_ANALISE_FINANCEIRA;
    }
}
