package com.vrumm.pedido.application.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Introspected
@Serdeable
public class PagamentoPedidoForm {

    @NotNull(message = "Informe o valor pago")
    @DecimalMin(value = "0.01", message = "Informe um valor pago maior que zero")
    private BigDecimal valorPago;

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }
}
