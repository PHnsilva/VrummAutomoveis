package com.vrumm.cliente.domain.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import java.math.BigDecimal;

@Introspected
@MappedEntity("cliente_rendimento")
public class ClienteRendimento {

    @Id
    @GeneratedValue
    private Long id;
    private Long clienteId;
    private Integer ordem;
    private String entidadeEmpregadora;
    private BigDecimal valorMensal;

    public ClienteRendimento() {
    }

    public ClienteRendimento(Long clienteId, Integer ordem, String entidadeEmpregadora, BigDecimal valorMensal) {
        setClienteId(clienteId);
        setOrdem(ordem);
        setEntidadeEmpregadora(entidadeEmpregadora);
        setValorMensal(valorMensal);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) {
        if (clienteId == null || clienteId <= 0) {
            throw new IllegalArgumentException("Cliente do rendimento é obrigatório");
        }
        this.clienteId = clienteId;
    }

    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) {
        if (ordem == null || ordem < 1 || ordem > 3) {
            throw new IllegalArgumentException("Ordem do rendimento deve estar entre 1 e 3");
        }
        this.ordem = ordem;
    }

    public String getEntidadeEmpregadora() { return entidadeEmpregadora; }
    public void setEntidadeEmpregadora(String entidadeEmpregadora) {
        if (entidadeEmpregadora == null || entidadeEmpregadora.isBlank()) {
            throw new IllegalArgumentException("Entidade empregadora é obrigatória");
        }
        String valorTratado = entidadeEmpregadora.trim();
        if (valorTratado.length() > 120) {
            throw new IllegalArgumentException("Entidade empregadora deve ter no máximo 120 caracteres");
        }
        this.entidadeEmpregadora = valorTratado;
    }

    public BigDecimal getValorMensal() { return valorMensal; }
    public void setValorMensal(BigDecimal valorMensal) {
        if (valorMensal == null || valorMensal.signum() <= 0) {
            throw new IllegalArgumentException("Valor mensal do rendimento deve ser maior que zero");
        }
        this.valorMensal = valorMensal;
    }

    public Rendimento toRendimento() {
        return new Rendimento(entidadeEmpregadora, valorMensal);
    }

    public static ClienteRendimento from(Long clienteId, Integer ordem, Rendimento rendimento) {
        return new ClienteRendimento(clienteId, ordem, rendimento.getDescricao(), rendimento.getValorMensal());
    }
}
