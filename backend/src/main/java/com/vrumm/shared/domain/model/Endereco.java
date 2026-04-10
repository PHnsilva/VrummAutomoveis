package com.vrumm.shared.domain.model;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class Endereco {

    private final String cep;
    private final String logradouro;
    private final String numero;
    private final String complemento;
    private final String bairro;
    private final String cidade;
    private final String uf;

    public Endereco(String cep,
                    String logradouro,
                    String numero,
                    String complemento,
                    String bairro,
                    String cidade,
                    String uf) {
        this.cep = validarCep(cep);
        this.logradouro = validarObrigatorio(logradouro, "Logradouro", 150);
        this.numero = validarObrigatorio(numero, "Número", 20);
        this.complemento = validarOpcional(complemento, "Complemento", 80);
        this.bairro = validarObrigatorio(bairro, "Bairro", 80);
        this.cidade = validarObrigatorio(cidade, "Cidade", 80);
        this.uf = validarUf(uf);
    }

    public String getCep() { return cep; }
    public String getLogradouro() { return logradouro; }
    public String getNumero() { return numero; }
    public String getComplemento() { return complemento; }
    public String getBairro() { return bairro; }
    public String getCidade() { return cidade; }
    public String getUf() { return uf; }

    public String getLinhaPrincipal() {
        return logradouro + ", " + numero + (complemento == null ? "" : " - " + complemento);
    }

    public String getLinhaSecundaria() {
        return bairro + " - " + cidade + "/" + uf + " - CEP " + cep;
    }

    public String formatado() {
        return getLinhaPrincipal() + ", " + getLinhaSecundaria();
    }

    private String validarCep(String cep) {
        if (cep == null || cep.isBlank()) {
            throw new IllegalArgumentException("CEP é obrigatório");
        }
        String cepNormalizado = cep.replaceAll("\\D", "");
        if (cepNormalizado.length() != 8) {
            throw new IllegalArgumentException("CEP deve conter 8 dígitos");
        }
        return cepNormalizado.substring(0, 5) + "-" + cepNormalizado.substring(5);
    }

    private String validarUf(String uf) {
        if (uf == null || uf.isBlank()) {
            throw new IllegalArgumentException("UF é obrigatória");
        }
        String ufTratada = uf.trim().toUpperCase();
        if (ufTratada.length() != 2) {
            throw new IllegalArgumentException("UF deve conter 2 caracteres");
        }
        return ufTratada;
    }

    private String validarObrigatorio(String valor, String campo, int maximo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " é obrigatório");
        }
        String tratado = valor.trim();
        if (tratado.length() > maximo) {
            throw new IllegalArgumentException(campo + " deve ter no máximo " + maximo + " caracteres");
        }
        return tratado;
    }

    private String validarOpcional(String valor, String campo, int maximo) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        String tratado = valor.trim();
        if (tratado.length() > maximo) {
            throw new IllegalArgumentException(campo + " deve ter no máximo " + maximo + " caracteres");
        }
        return tratado;
    }
}
