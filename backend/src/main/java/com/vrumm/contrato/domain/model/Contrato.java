package com.vrumm.contrato.domain.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;

import java.time.LocalDateTime;

@Introspected
@MappedEntity("contrato")
public class Contrato {
    @Id
    @GeneratedValue
    private Long id;
    private Long pedidoId;
    @TypeDef(type = DataType.STRING)
    private TipoContrato tipoContrato;
    @TypeDef(type = DataType.STRING)
    private TipoProprietario tipoProprietario;
    private String observacao;
    private LocalDateTime dataCriacao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { if (pedidoId == null || pedidoId <= 0) throw new IllegalArgumentException("Pedido do contrato é obrigatório"); this.pedidoId = pedidoId; }
    public TipoContrato getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(TipoContrato tipoContrato) { if (tipoContrato == null) throw new IllegalArgumentException("Tipo do contrato é obrigatório"); this.tipoContrato = tipoContrato; }
    public TipoProprietario getTipoProprietario() { return tipoProprietario; }
    public void setTipoProprietario(TipoProprietario tipoProprietario) { if (tipoProprietario == null) throw new IllegalArgumentException("Tipo de proprietário é obrigatório"); this.tipoProprietario = tipoProprietario; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { if (observacao == null || observacao.isBlank()) { this.observacao = null; return; } String v = observacao.trim(); if (v.length() > 500) throw new IllegalArgumentException("Observação do contrato deve ter no máximo 500 caracteres"); this.observacao = v; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { if (dataCriacao == null) throw new IllegalArgumentException("Data do contrato é obrigatória"); this.dataCriacao = dataCriacao; }
    public void prepararNovoRegistro(Long pedidoId, TipoContrato tipoContrato, TipoProprietario tipoProprietario, String observacao) { setPedidoId(pedidoId); setTipoContrato(tipoContrato); setTipoProprietario(tipoProprietario); setObservacao(observacao); setDataCriacao(LocalDateTime.now()); }
}
