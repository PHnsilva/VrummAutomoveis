package com.vrumm.contrato.domain.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Introspected
@MappedEntity("contrato_credito")
public class ContratoCredito {
    @Id
    @GeneratedValue
    private Long id;
    private Long pedidoId;
    private Long bancoId;
    private String numeroReferencia;
    private BigDecimal valorCredito;
    private String observacao;
    private LocalDateTime dataCriacao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { if (pedidoId == null || pedidoId <= 0) throw new IllegalArgumentException("Pedido do contrato de crédito é obrigatório"); this.pedidoId = pedidoId; }
    public Long getBancoId() { return bancoId; }
    public void setBancoId(Long bancoId) { if (bancoId == null || bancoId <= 0) throw new IllegalArgumentException("Banco do contrato de crédito é obrigatório"); this.bancoId = bancoId; }
    public String getNumeroReferencia() { return numeroReferencia; }
    public void setNumeroReferencia(String numeroReferencia) { if (numeroReferencia == null || numeroReferencia.isBlank()) throw new IllegalArgumentException("Número de referência é obrigatório"); String v = numeroReferencia.trim(); if (v.length() > 50) throw new IllegalArgumentException("Número de referência deve ter no máximo 50 caracteres"); this.numeroReferencia = v; }
    public BigDecimal getValorCredito() { return valorCredito; }
    public void setValorCredito(BigDecimal valorCredito) { if (valorCredito == null || valorCredito.signum() <= 0) throw new IllegalArgumentException("Valor de crédito deve ser maior que zero"); this.valorCredito = valorCredito; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { if (observacao == null || observacao.isBlank()) { this.observacao = null; return; } String v = observacao.trim(); if (v.length() > 500) throw new IllegalArgumentException("Observação do contrato de crédito deve ter no máximo 500 caracteres"); this.observacao = v; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { if (dataCriacao == null) throw new IllegalArgumentException("Data do contrato de crédito é obrigatória"); this.dataCriacao = dataCriacao; }
    public void prepararNovoRegistro(Long pedidoId, Long bancoId, String numeroReferencia, BigDecimal valorCredito, String observacao) { setPedidoId(pedidoId); setBancoId(bancoId); setNumeroReferencia(numeroReferencia); setValorCredito(valorCredito); setObservacao(observacao); setDataCriacao(LocalDateTime.now()); }
}
