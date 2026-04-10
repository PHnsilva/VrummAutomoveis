package com.vrumm.pedido.application.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Size;

@Introspected
@Serdeable
public class AvaliacaoPedidoForm {

    @Size(max = 500)
    private String parecerObservacao;

    public String getParecerObservacao() {
        return parecerObservacao;
    }

    public void setParecerObservacao(String parecerObservacao) {
        this.parecerObservacao = parecerObservacao;
    }
}
