package com.vrumm.pedido.application.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class PedidoStatusDto {

    private Long id;
    private String clienteNome;
    private String clienteEmail;
    private String automovelDescricao;
    private String statusCodigo;
    private String statusDescricao;
    private String statusBadgeClass;
    private String dataCriacaoFormatada;
    private String observacao;
    private String valorEntradaFormatado;
    private Integer prazoMeses;
    private String rendaDeclaradaFormatada;
    private String valorPagoFormatado;
    private boolean podeConfirmarPagamento;
    private boolean podeAvancarParaPagamento;
    private boolean podeRecusar;
    private boolean finalizado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }

    public String getAutomovelDescricao() {
        return automovelDescricao;
    }

    public void setAutomovelDescricao(String automovelDescricao) {
        this.automovelDescricao = automovelDescricao;
    }

    public String getStatusCodigo() {
        return statusCodigo;
    }

    public void setStatusCodigo(String statusCodigo) {
        this.statusCodigo = statusCodigo;
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

    public String getValorPagoFormatado() {
        return valorPagoFormatado;
    }

    public void setValorPagoFormatado(String valorPagoFormatado) {
        this.valorPagoFormatado = valorPagoFormatado;
    }

    public boolean isPodeConfirmarPagamento() {
        return podeConfirmarPagamento;
    }

    public void setPodeConfirmarPagamento(boolean podeConfirmarPagamento) {
        this.podeConfirmarPagamento = podeConfirmarPagamento;
    }

    public boolean isPodeAvancarParaPagamento() {
        return podeAvancarParaPagamento;
    }

    public void setPodeAvancarParaPagamento(boolean podeAvancarParaPagamento) {
        this.podeAvancarParaPagamento = podeAvancarParaPagamento;
    }

    public boolean isPodeRecusar() {
        return podeRecusar;
    }

    public void setPodeRecusar(boolean podeRecusar) {
        this.podeRecusar = podeRecusar;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }
}
