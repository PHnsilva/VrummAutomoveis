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
        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }

        int resto = 11 - (soma % 11);
        int digito1 = (resto >= 10) ? 0 : resto;

        if (digito1 != (cpf.charAt(9) - '0')) {
            return false;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }

        resto = 11 - (soma % 11);
        int digito2 = (resto >= 10) ? 0 : resto;

        return digito2 == (cpf.charAt(10) - '0');
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
