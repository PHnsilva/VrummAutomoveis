package com.vrumm.cliente.application.dto;

import com.vrumm.cliente.application.mapper.ClienteMapper;
import com.vrumm.cliente.domain.model.Cliente;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.ArrayList;
import java.util.List;

@Introspected
@Serdeable
public class ClienteResponse {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String rg;
    private String profissao;
    private String perfil;
    private String endereco;
    private List<String> rendimentos = new ArrayList<>();

    public static ClienteResponse fromCliente(Cliente cliente) {
        return ClienteMapper.toResponse(cliente);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public List<String> getRendimentos() { return rendimentos; }
    public void setRendimentos(List<String> rendimentos) { this.rendimentos = rendimentos; }
}
