package com.vrumm.cliente.domain.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Transient;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Introspected
@MappedEntity("cliente")
public class Cliente {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String email;
    @TypeDef(type = DataType.STRING)
    private Cpf cpf;
    private String rg;
    private String profissao;
    @TypeDef(type = DataType.STRING)
    private PerfilAcesso perfil = PerfilAcesso.CLIENTE;
    private Long empresaId;
    private Long bancoId;
    private String senhaHash;
    @Transient
    private List<Rendimento> rendimentos = new ArrayList<>();

    public Cliente() {}

    public Cliente(Long id, String nome, String email, Cpf cpf, String rg, String profissao, PerfilAcesso perfil, Long empresaId, Long bancoId, String senhaHash) {
        this.id = id;
        setNome(nome); setEmail(email); setCpf(cpf); setRg(rg); setProfissao(profissao); setPerfil(perfil); setEmpresaId(empresaId); setBancoId(bancoId); setSenhaHash(senhaHash);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome do cliente é obrigatório"); String v = nome.trim(); if (v.length() > 150) throw new IllegalArgumentException("Nome do cliente deve ter no máximo 150 caracteres"); this.nome = v; }
    public String getEmail() { return email; }
    public void setEmail(String email) { if (email == null || email.isBlank()) throw new IllegalArgumentException("E-mail do cliente é obrigatório"); String v = email.trim().toLowerCase(); if (v.length() > 150) throw new IllegalArgumentException("E-mail do cliente deve ter no máximo 150 caracteres"); this.email = v; }
    public Cpf getCpf() { return cpf; }
    public void setCpf(Cpf cpf) { if (cpf == null) throw new IllegalArgumentException("CPF do cliente é obrigatório"); this.cpf = cpf; }
    public String getRg() { return rg; }
    public void setRg(String rg) { if (rg == null || rg.isBlank()) throw new IllegalArgumentException("RG do cliente é obrigatório"); String v = rg.trim(); if (v.length() > 20) throw new IllegalArgumentException("RG do cliente deve ter no máximo 20 caracteres"); this.rg = v; }
    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { if (profissao == null || profissao.isBlank()) throw new IllegalArgumentException("Profissão do cliente é obrigatória"); String v = profissao.trim(); if (v.length() > 100) throw new IllegalArgumentException("Profissão do cliente deve ter no máximo 100 caracteres"); this.profissao = v; }
    public PerfilAcesso getPerfil() { return perfil; }
    public void setPerfil(PerfilAcesso perfil) { this.perfil = perfil == null ? PerfilAcesso.CLIENTE : perfil; }
    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long empresaId) { this.empresaId = empresaId; }
    public Long getBancoId() { return bancoId; }
    public void setBancoId(Long bancoId) { this.bancoId = bancoId; }
    @Transient
    public boolean isEmpresa() { return perfil == PerfilAcesso.EMPRESA; }
    @Transient
    public boolean isBanco() { return perfil == PerfilAcesso.BANCO; }
    @Transient
    public boolean isCliente() { return perfil == PerfilAcesso.CLIENTE; }
    @Transient
    public boolean isAgente() { return isEmpresa() || isBanco(); }
    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { if (senhaHash == null || senhaHash.isBlank()) throw new IllegalArgumentException("Senha do cliente é obrigatória"); this.senhaHash = senhaHash; }
    public List<Rendimento> getRendimentos() { return Collections.unmodifiableList(rendimentos); }
    public void adicionarRendimento(Rendimento rendimento) { if (rendimento == null) throw new IllegalArgumentException("Rendimento é obrigatório"); if (rendimentos.size() >= 3) throw new IllegalStateException("Cliente pode possuir no máximo 3 rendimentos"); rendimentos.add(rendimento); }
    public boolean podeSerRemovido(boolean possuiVinculosAtivos) { return !possuiVinculosAtivos; }
}
