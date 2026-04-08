package com.vrumm.pedido.application.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class PedidoStatusDto {

    private Long id;
    private String automovelDescricao;
    private String statusDescricao;
    private String statusBadgeClass;
    private String dataCriacaoFormatada;
    private String observacao;
    private String valorEntradaFormatado;
    private Integer prazoMeses;
    private String rendaDeclaradaFormatada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutomovelDescricao() {
        return automovelDescricao;
    }

    public void setAutomovelDescricao(String automovelDescricao) {
        this.automovelDescricao = automovelDescricao;
    }

    public String getStatusDescricao() {
        return statusDescricao;
    }

    public void setStatusDescricao(String statusDescricao) {
        this.statusDescricao = statusDescricao;
    }

    public String getStatusBadgeClass() {
        return statusBadgeClass;
    }

    public void setStatusBadgeClass(String statusBadgeClass) {
        this.statusBadgeClass = statusBadgeClass;
    }

    public String getDataCriacaoFormatada() {
        return dataCriacaoFormatada;
    }

    public void setDataCriacaoFormatada(String dataCriacaoFormatada) {
        this.dataCriacaoFormatada = dataCriacaoFormatada;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getValorEntradaFormatado() {
        return valorEntradaFormatado;
    }

    public void setValorEntradaFormatado(String valorEntradaFormatado) {
        this.valorEntradaFormatado = valorEntradaFormatado;
    }

    public Integer getPrazoMeses() {
        return prazoMeses;
    }

    public void setPrazoMeses(Integer prazoMeses) {
        this.prazoMeses = prazoMeses;
    }

    public String getRendaDeclaradaFormatada() {
        return rendaDeclaradaFormatada;
    }

    public void setRendaDeclaradaFormatada(String rendaDeclaradaFormatada) {
        this.rendaDeclaradaFormatada = rendaDeclaradaFormatada;
    }
}
