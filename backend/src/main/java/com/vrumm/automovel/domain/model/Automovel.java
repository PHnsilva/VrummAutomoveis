package com.vrumm.automovel.domain.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Transient;

@Introspected
@MappedEntity("automovel")
public class Automovel {

    @Id
    @GeneratedValue
    private Long id;
    private String marca;
    private String modelo;
    private Integer anoModelo;
    private String cor;
    private boolean disponivel = true;

    public Automovel() {
    }

    public Automovel(Long id, String marca, String modelo, Integer anoModelo, String cor, boolean disponivel) {
        this.id = id;
        setMarca(marca);
        setModelo(modelo);
        setAnoModelo(anoModelo);
        setCor(cor);
        this.disponivel = disponivel;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) {
        if (marca == null || marca.isBlank()) {
            throw new IllegalArgumentException("Marca do automóvel é obrigatória");
        }
        String valorTratado = marca.trim();
        if (valorTratado.length() > 80) {
            throw new IllegalArgumentException("Marca do automóvel deve ter no máximo 80 caracteres");
        }
        this.marca = valorTratado;
    }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) {
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("Modelo do automóvel é obrigatório");
        }
        String valorTratado = modelo.trim();
        if (valorTratado.length() > 120) {
            throw new IllegalArgumentException("Modelo do automóvel deve ter no máximo 120 caracteres");
        }
        this.modelo = valorTratado;
    }

    public Integer getAnoModelo() { return anoModelo; }
    public void setAnoModelo(Integer anoModelo) {
        if (anoModelo == null || anoModelo < 1900 || anoModelo > 2100) {
            throw new IllegalArgumentException("Ano do automóvel é inválido");
        }
        this.anoModelo = anoModelo;
    }

    public String getCor() { return cor; }
    public void setCor(String cor) {
        if (cor == null || cor.isBlank()) {
            this.cor = null;
            return;
        }
        String valorTratado = cor.trim();
        if (valorTratado.length() > 40) {
            throw new IllegalArgumentException("Cor do automóvel deve ter no máximo 40 caracteres");
        }
        this.cor = valorTratado;
    }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    @Transient
    public String getDescricaoExibicao() {
        String descricao = marca + " " + modelo + " (" + anoModelo + ")";
        return cor == null ? descricao : descricao + " - " + cor;
    }
}
