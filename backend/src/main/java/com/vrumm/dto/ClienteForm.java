package com.vrumm.dto;

import com.vrumm.domain.Cliente;
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

    private String senha;

    public static ClienteForm fromCliente(Cliente cliente) {
        ClienteForm form = new ClienteForm();
        form.setNome(cliente.getNome());
        form.setEmail(cliente.getEmail());
        form.setCpf(cliente.getCpf().getValor());
        form.setRg(cliente.getRg());
        form.setProfissao(cliente.getProfissao());
        return form;
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
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
