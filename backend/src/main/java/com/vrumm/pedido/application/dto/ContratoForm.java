package com.vrumm.pedido.application.dto;

import com.vrumm.contrato.domain.model.TipoContrato;
import com.vrumm.contrato.domain.model.TipoProprietario;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Introspected
@Serdeable
public class ContratoForm {
    @NotNull
    private TipoContrato tipoContrato;
    @NotNull
    private TipoProprietario tipoProprietario;
    @Size(max = 500)
    private String observacao;
    public TipoContrato getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(TipoContrato tipoContrato) { this.tipoContrato = tipoContrato; }
    public TipoProprietario getTipoProprietario() { return tipoProprietario; }
    public void setTipoProprietario(TipoProprietario tipoProprietario) { this.tipoProprietario = tipoProprietario; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
