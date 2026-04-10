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
    private boolean podeRecusar;
    private boolean podeEditar;
    private boolean podeCancelar;
    private boolean finalizado;
    private boolean cancelado;
    private String parecerFinanceiroDescricao;
    private String parecerFinanceiroObservacao;
    private String dataParecerFinanceiroFormatada;
    private String bancoAvaliadorNome;
    private boolean possuiContrato;
    private String tipoContratoDescricao;
    private String tipoProprietarioDescricao;
    private String observacaoContrato;
    private boolean possuiContratoCredito;
    private String numeroReferenciaCredito;
    private String valorCreditoFormatado;
    private String observacaoCredito;
    private String bancoCreditoNome;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getClienteNome() { return clienteNome; }
    public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }
    public String getClienteEmail() { return clienteEmail; }
    public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }
    public String getAutomovelDescricao() { return automovelDescricao; }
    public void setAutomovelDescricao(String automovelDescricao) { this.automovelDescricao = automovelDescricao; }
    public String getStatusCodigo() { return statusCodigo; }
    public void setStatusCodigo(String statusCodigo) { this.statusCodigo = statusCodigo; }
    public String getStatusDescricao() { return statusDescricao; }
    public void setStatusDescricao(String statusDescricao) { this.statusDescricao = statusDescricao; }
    public String getStatusBadgeClass() { return statusBadgeClass; }
    public void setStatusBadgeClass(String statusBadgeClass) { this.statusBadgeClass = statusBadgeClass; }
    public String getDataCriacaoFormatada() { return dataCriacaoFormatada; }
    public void setDataCriacaoFormatada(String dataCriacaoFormatada) { this.dataCriacaoFormatada = dataCriacaoFormatada; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public String getValorEntradaFormatado() { return valorEntradaFormatado; }
    public void setValorEntradaFormatado(String valorEntradaFormatado) { this.valorEntradaFormatado = valorEntradaFormatado; }
    public Integer getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(Integer prazoMeses) { this.prazoMeses = prazoMeses; }
    public String getRendaDeclaradaFormatada() { return rendaDeclaradaFormatada; }
    public void setRendaDeclaradaFormatada(String rendaDeclaradaFormatada) { this.rendaDeclaradaFormatada = rendaDeclaradaFormatada; }
    public String getValorPagoFormatado() { return valorPagoFormatado; }
    public void setValorPagoFormatado(String valorPagoFormatado) { this.valorPagoFormatado = valorPagoFormatado; }
    public boolean isPodeConfirmarPagamento() { return podeConfirmarPagamento; }
    public void setPodeConfirmarPagamento(boolean podeConfirmarPagamento) { this.podeConfirmarPagamento = podeConfirmarPagamento; }
    public boolean isPodeRecusar() { return podeRecusar; }
    public void setPodeRecusar(boolean podeRecusar) { this.podeRecusar = podeRecusar; }
    public boolean isPodeEditar() { return podeEditar; }
    public void setPodeEditar(boolean podeEditar) { this.podeEditar = podeEditar; }
    public boolean isPodeCancelar() { return podeCancelar; }
    public void setPodeCancelar(boolean podeCancelar) { this.podeCancelar = podeCancelar; }
    public boolean isFinalizado() { return finalizado; }
    public void setFinalizado(boolean finalizado) { this.finalizado = finalizado; }
    public boolean isCancelado() { return cancelado; }
    public void setCancelado(boolean cancelado) { this.cancelado = cancelado; }
    public String getParecerFinanceiroDescricao() { return parecerFinanceiroDescricao; }
    public void setParecerFinanceiroDescricao(String parecerFinanceiroDescricao) { this.parecerFinanceiroDescricao = parecerFinanceiroDescricao; }
    public String getParecerFinanceiroObservacao() { return parecerFinanceiroObservacao; }
    public void setParecerFinanceiroObservacao(String parecerFinanceiroObservacao) { this.parecerFinanceiroObservacao = parecerFinanceiroObservacao; }
    public String getDataParecerFinanceiroFormatada() { return dataParecerFinanceiroFormatada; }
    public void setDataParecerFinanceiroFormatada(String dataParecerFinanceiroFormatada) { this.dataParecerFinanceiroFormatada = dataParecerFinanceiroFormatada; }
    public String getBancoAvaliadorNome() { return bancoAvaliadorNome; }
    public void setBancoAvaliadorNome(String bancoAvaliadorNome) { this.bancoAvaliadorNome = bancoAvaliadorNome; }
    public boolean isPossuiContrato() { return possuiContrato; }
    public void setPossuiContrato(boolean possuiContrato) { this.possuiContrato = possuiContrato; }
    public String getTipoContratoDescricao() { return tipoContratoDescricao; }
    public void setTipoContratoDescricao(String tipoContratoDescricao) { this.tipoContratoDescricao = tipoContratoDescricao; }
    public String getTipoProprietarioDescricao() { return tipoProprietarioDescricao; }
    public void setTipoProprietarioDescricao(String tipoProprietarioDescricao) { this.tipoProprietarioDescricao = tipoProprietarioDescricao; }
    public String getObservacaoContrato() { return observacaoContrato; }
    public void setObservacaoContrato(String observacaoContrato) { this.observacaoContrato = observacaoContrato; }
    public boolean isPossuiContratoCredito() { return possuiContratoCredito; }
    public void setPossuiContratoCredito(boolean possuiContratoCredito) { this.possuiContratoCredito = possuiContratoCredito; }
    public String getNumeroReferenciaCredito() { return numeroReferenciaCredito; }
    public void setNumeroReferenciaCredito(String numeroReferenciaCredito) { this.numeroReferenciaCredito = numeroReferenciaCredito; }
    public String getValorCreditoFormatado() { return valorCreditoFormatado; }
    public void setValorCreditoFormatado(String valorCreditoFormatado) { this.valorCreditoFormatado = valorCreditoFormatado; }
    public String getObservacaoCredito() { return observacaoCredito; }
    public void setObservacaoCredito(String observacaoCredito) { this.observacaoCredito = observacaoCredito; }
    public String getBancoCreditoNome() { return bancoCreditoNome; }
    public void setBancoCreditoNome(String bancoCreditoNome) { this.bancoCreditoNome = bancoCreditoNome; }
}
