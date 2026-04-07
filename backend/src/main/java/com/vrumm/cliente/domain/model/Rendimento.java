package com.vrumm.cliente.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Rendimento {

    private final String descricao;
    private final BigDecimal valorMensal;

    public Rendimento(String descricao, BigDecimal valorMensal) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição do rendimento é obrigatória");
        }

        String descricaoTratada = descricao.trim();

        if (descricaoTratada.length() > 100) {
            throw new IllegalArgumentException("Descrição do rendimento deve ter no máximo 100 caracteres");
        }

        if (valorMensal == null) {
            throw new IllegalArgumentException("Valor mensal do rendimento é obrigatório");
        }

        if (valorMensal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor mensal do rendimento deve ser maior que zero");
        }

        this.descricao = descricaoTratada;
        this.valorMensal = valorMensal;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValorMensal() {
        return valorMensal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rendimento that)) return false;
        return Objects.equals(descricao, that.descricao)
                && Objects.equals(valorMensal, that.valorMensal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao, valorMensal);
    }
}
