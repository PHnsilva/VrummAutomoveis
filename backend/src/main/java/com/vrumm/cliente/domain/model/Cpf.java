package com.vrumm.cliente.domain.model;

import java.util.Objects;

public class Cpf {

    private final String valor;

    public Cpf(String valor) {
        String normalizado = normalizar(valor);

        if (!isValido(normalizado)) {
            throw new IllegalArgumentException("CPF inválido");
        }

        this.valor = normalizado;
    }

    public String getValor() {
        return valor;
    }

    private String normalizar(String cpf) {
        if (cpf == null) {
            return "";
        }
        return cpf.replaceAll("\\D", "");
    }

    private boolean isValido(String cpf) {
        return cpf.length() == 11;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cpf cpf)) return false;
        return Objects.equals(valor, cpf.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}
