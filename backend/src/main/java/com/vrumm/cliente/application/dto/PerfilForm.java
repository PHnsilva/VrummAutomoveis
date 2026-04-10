package com.vrumm.cliente.application.dto;

import com.vrumm.cliente.domain.model.Cliente;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Introspected
@Serdeable
public class PerfilForm {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
    private String nome;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres")
    private String email;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    private String cpf;

    @NotBlank(message = "RG é obrigatório")
    @Size(max = 20, message = "RG deve ter no máximo 20 caracteres")
    private String rg;

    @NotBlank(message = "Profissão é obrigatória")
    @Size(max = 100, message = "Profissão deve ter no máximo 100 caracteres")
    private String profissao;

    @Size(min = 3, max = 100, message = "Nova senha deve ter entre 3 e 100 caracteres")
    private String senha;

    public static PerfilForm fromCliente(Cliente cliente) {
        PerfilForm form = new PerfilForm();
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
    public void setSenha(String senha) {
        this.senha = senha != null && senha.isBlank() ? null : senha;
    }
}