package com.vrumm.pedido.application.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Introspected
@Serdeable
public class FinanceiroAvaliacaoForm {
    @NotNull
    private Boolean favoravel;
    @Size(max = 500)
    private String observacao;
    public Boolean getFavoravel() { return favoravel; }
    public void setFavoravel(Boolean favoravel) { this.favoravel = favoravel; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
