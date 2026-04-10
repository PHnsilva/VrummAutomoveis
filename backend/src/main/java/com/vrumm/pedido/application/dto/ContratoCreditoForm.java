package com.vrumm.pedido.application.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Introspected
@Serdeable
public class ContratoCreditoForm {
    @NotBlank
    @Size(max = 50)
    private String numeroReferencia;
    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal valorCredito;
    @Size(max = 500)
    private String observacao;
    public String getNumeroReferencia() { return numeroReferencia; }
    public void setNumeroReferencia(String numeroReferencia) { this.numeroReferencia = numeroReferencia; }
    public BigDecimal getValorCredito() { return valorCredito; }
    public void setValorCredito(BigDecimal valorCredito) { this.valorCredito = valorCredito; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
