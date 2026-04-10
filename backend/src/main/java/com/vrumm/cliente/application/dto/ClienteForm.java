package com.vrumm.cliente.application.dto;

import com.vrumm.cliente.application.mapper.ClienteMapper;
import com.vrumm.cliente.domain.model.Cliente;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Introspected
@Serdeable
public class ClienteForm {

    @NotBlank
    @Size(max = 150)
    private String nome;

    @NotBlank
    @Email
    @Size(max = 150)
    private String email;

    @NotBlank
    @Pattern(regexp = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF inválido")
    private String cpf;

    @NotBlank
    @Size(max = 20)
    private String rg;

    @NotBlank
    @Size(max = 100)
    private String profissao;

    @NotBlank
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP inválido")
    private String enderecoCep;

    @NotBlank
    @Size(max = 150)
    private String enderecoLogradouro;

    @NotBlank
    @Size(max = 20)
    private String enderecoNumero;

    @Size(max = 80)
    private String enderecoComplemento;

    @NotBlank
    @Size(max = 80)
    private String enderecoBairro;

    @NotBlank
    @Size(max = 80)
    private String enderecoCidade;

    @NotBlank
    @Pattern(regexp = "[A-Za-z]{2}", message = "UF inválida")
    private String enderecoUf;

    @Size(max = 120)
    private String entidadeEmpregadora1;
    private String valorRendimento1;

    @Size(max = 120)
    private String entidadeEmpregadora2;
    private String valorRendimento2;

    @Size(max = 120)
    private String entidadeEmpregadora3;
    private String valorRendimento3;

    private String senha;

    public static ClienteForm fromCliente(Cliente cliente) {
        return ClienteMapper.toForm(cliente);
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }
    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }
    public String getEnderecoCep() { return enderecoCep; }
    public void setEnderecoCep(String enderecoCep) { this.enderecoCep = enderecoCep; }
    public String getEnderecoLogradouro() { return enderecoLogradouro; }
    public void setEnderecoLogradouro(String enderecoLogradouro) { this.enderecoLogradouro = enderecoLogradouro; }
    public String getEnderecoNumero() { return enderecoNumero; }
    public void setEnderecoNumero(String enderecoNumero) { this.enderecoNumero = enderecoNumero; }
    public String getEnderecoComplemento() { return enderecoComplemento; }
    public void setEnderecoComplemento(String enderecoComplemento) { this.enderecoComplemento = enderecoComplemento; }
    public String getEnderecoBairro() { return enderecoBairro; }
    public void setEnderecoBairro(String enderecoBairro) { this.enderecoBairro = enderecoBairro; }
    public String getEnderecoCidade() { return enderecoCidade; }
    public void setEnderecoCidade(String enderecoCidade) { this.enderecoCidade = enderecoCidade; }
    public String getEnderecoUf() { return enderecoUf; }
    public void setEnderecoUf(String enderecoUf) { this.enderecoUf = enderecoUf; }
    public String getEntidadeEmpregadora1() { return entidadeEmpregadora1; }
    public void setEntidadeEmpregadora1(String entidadeEmpregadora1) { this.entidadeEmpregadora1 = entidadeEmpregadora1; }
    public String getValorRendimento1() { return valorRendimento1; }
    public void setValorRendimento1(String valorRendimento1) { this.valorRendimento1 = normalizarNumero(valorRendimento1); }
    public String getEntidadeEmpregadora2() { return entidadeEmpregadora2; }
    public void setEntidadeEmpregadora2(String entidadeEmpregadora2) { this.entidadeEmpregadora2 = entidadeEmpregadora2; }
    public String getValorRendimento2() { return valorRendimento2; }
    public void setValorRendimento2(String valorRendimento2) { this.valorRendimento2 = normalizarNumero(valorRendimento2); }
    public String getEntidadeEmpregadora3() { return entidadeEmpregadora3; }
    public void setEntidadeEmpregadora3(String entidadeEmpregadora3) { this.entidadeEmpregadora3 = entidadeEmpregadora3; }
    public String getValorRendimento3() { return valorRendimento3; }
    public void setValorRendimento3(String valorRendimento3) { this.valorRendimento3 = normalizarNumero(valorRendimento3); }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    private String normalizarNumero(String valor) {
        if (valor == null) return null;
        String texto = valor.trim();
        return texto.isEmpty() ? null : texto;
    }
}
