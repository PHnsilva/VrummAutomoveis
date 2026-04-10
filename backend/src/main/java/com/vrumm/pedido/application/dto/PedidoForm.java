package com.vrumm.pedido.application.dto;

import com.vrumm.pedido.domain.model.PedidoAluguel;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Introspected
@Serdeable
public class PedidoForm {

    @NotNull
    private Long automovelId;

    @Size(max = 500)
    private String observacao;

    private String valorEntrada;
    private String prazoMeses;
    private String rendaDeclarada;

    public static PedidoForm fromPedido(PedidoAluguel pedido) {
        PedidoForm form = new PedidoForm();
        form.setAutomovelId(pedido.getAutomovelId());
        form.setObservacao(pedido.getObservacao());
        form.setValorEntrada(formatarDecimal(pedido.getValorEntrada()));
        form.setPrazoMeses(formatarInteiro(pedido.getPrazoMeses()));
        form.setRendaDeclarada(formatarDecimal(pedido.getRendaDeclarada()));
        return form;
    }

    public Long getAutomovelId() { return automovelId; }
    public void setAutomovelId(Long automovelId) { this.automovelId = automovelId; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public String getValorEntrada() { return valorEntrada; }
    public void setValorEntrada(String valorEntrada) { this.valorEntrada = normalizarNumero(valorEntrada); }

    public String getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(String prazoMeses) { this.prazoMeses = normalizarNumero(prazoMeses); }

    public String getRendaDeclarada() { return rendaDeclarada; }
    public void setRendaDeclarada(String rendaDeclarada) { this.rendaDeclarada = normalizarNumero(rendaDeclarada); }

    public BigDecimal getValorEntradaBigDecimal() { return parseDecimal(valorEntrada, "Valor de entrada"); }
    public Integer getPrazoMesesInteger() { return parseInteger(prazoMeses, "Prazo desejado em meses"); }
    public BigDecimal getRendaDeclaradaBigDecimal() { return parseDecimal(rendaDeclarada, "Renda declarada"); }

    private static String normalizarNumero(String valor) {
        if (valor == null) return null;
        String texto = valor.trim();
        return texto.isEmpty() ? null : texto;
    }

    private static BigDecimal parseDecimal(String valor, String campo) {
        if (valor == null || valor.isBlank()) return null;
        try {
            return new BigDecimal(valor.replace(',', '.').trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(campo + " inválido");
        }
    }

    private static Integer parseInteger(String valor, String campo) {
        if (valor == null || valor.isBlank()) return null;
        try {
            return Integer.valueOf(valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(campo + " inválido");
        }
    }

    private static String formatarDecimal(BigDecimal valor) {
        return valor == null ? null : valor.stripTrailingZeros().toPlainString();
    }

    private static String formatarInteiro(Integer valor) {
        return valor == null ? null : String.valueOf(valor);
    }
}
