package com.vrumm.pedido.domain.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Introspected
@MappedEntity("pedido_aluguel")
public class PedidoAluguel {

    @Id
    @GeneratedValue
    private Long id;
    private Long clienteId;
    private Long automovelId;

    @TypeDef(type = DataType.STRING)
    private PedidoStatus status;

    private LocalDateTime dataCriacao;
    private String observacao;
    private BigDecimal valorEntrada;
    private Integer prazoMeses;
    private BigDecimal rendaDeclarada;
    private BigDecimal valorPago;

    public PedidoAluguel() {
    }

    public static PedidoAluguel criarSolicitacao(Long clienteId,
                                                 Long automovelId,
                                                 String observacao,
                                                 BigDecimal valorEntrada,
                                                 Integer prazoMeses,
                                                 BigDecimal rendaDeclarada) {
        PedidoAluguel pedido = new PedidoAluguel();
        pedido.setClienteId(clienteId);
        pedido.setAutomovelId(automovelId);
        pedido.iniciarFluxo();
        pedido.setObservacao(observacao);
        pedido.setValorEntrada(valorEntrada);
        pedido.setPrazoMeses(prazoMeses);
        pedido.setRendaDeclarada(rendaDeclarada);
        return pedido;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) {
        if (clienteId == null || clienteId <= 0) {
            throw new IllegalArgumentException("Cliente do pedido é obrigatório");
        }
        this.clienteId = clienteId;
    }

    public Long getAutomovelId() { return automovelId; }
    public void setAutomovelId(Long automovelId) {
        if (automovelId == null || automovelId <= 0) {
            throw new IllegalArgumentException("Automóvel do pedido é obrigatório");
        }
        this.automovelId = automovelId;
    }

    public PedidoStatus getStatus() { return status; }
    public void setStatus(PedidoStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status do pedido é obrigatório");
        }
        this.status = status;
    }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) {
        if (dataCriacao == null) {
            throw new IllegalArgumentException("Data de criação do pedido é obrigatória");
        }
        this.dataCriacao = dataCriacao;
    }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) {
        if (observacao == null || observacao.isBlank()) {
            this.observacao = null;
            return;
        }
        String valorTratado = observacao.trim();
        if (valorTratado.length() > 500) {
            throw new IllegalArgumentException("Observação do pedido deve ter no máximo 500 caracteres");
        }
        this.observacao = valorTratado;
    }

    public BigDecimal getValorEntrada() { return valorEntrada; }
    public void setValorEntrada(BigDecimal valorEntrada) {
        if (valorEntrada != null && valorEntrada.signum() < 0) {
            throw new IllegalArgumentException("Valor de entrada não pode ser negativo");
        }
        this.valorEntrada = valorEntrada;
    }

    public Integer getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(Integer prazoMeses) {
        if (prazoMeses != null && prazoMeses <= 0) {
            throw new IllegalArgumentException("Prazo do pedido deve ser maior que zero");
        }
        this.prazoMeses = prazoMeses;
    }

    public BigDecimal getRendaDeclarada() { return rendaDeclarada; }
    public void setRendaDeclarada(BigDecimal rendaDeclarada) {
        if (rendaDeclarada != null && rendaDeclarada.signum() < 0) {
            throw new IllegalArgumentException("Renda declarada não pode ser negativa");
        }
        this.rendaDeclarada = rendaDeclarada;
    }

    public BigDecimal getValorPago() { return valorPago; }
    public void setValorPago(BigDecimal valorPago) {
        if (valorPago != null && valorPago.signum() < 0) {
            throw new IllegalArgumentException("Valor pago não pode ser negativo");
        }
        this.valorPago = valorPago;
    }

    public boolean pertenceAoCliente(Long idCliente) {
        return clienteId != null && clienteId.equals(idCliente);
    }

    public void iniciarFluxo() {
        setStatus(PedidoStatus.statusInicial());
        setDataCriacao(LocalDateTime.now());
    }

    public void recusar() {
        if (status == PedidoStatus.FINALIZADO) {
            throw new IllegalStateException("Pedido finalizado não pode ser recusado");
        }
        if (status == PedidoStatus.RECUSADO) {
            throw new IllegalStateException("Pedido já foi recusado");
        }
        setStatus(PedidoStatus.RECUSADO);
    }

    public void finalizar(BigDecimal valorPago) {
        if (status != PedidoStatus.AGUARDANDO_PAGAMENTO) {
            throw new IllegalStateException("Pedido não está aguardando pagamento");
        }
        if (valorPago == null || valorPago.signum() <= 0) {
            throw new IllegalArgumentException("Informe um valor pago maior que zero");
        }
        setValorPago(valorPago);
        setStatus(PedidoStatus.FINALIZADO);
    }

    public boolean podeIrParaPagamento() {
        return false;
    }

    public boolean podeSerRecusado() {
        return status == PedidoStatus.AGUARDANDO_PAGAMENTO;
    }

    public boolean podeConfirmarPagamento() {
        return status == PedidoStatus.AGUARDANDO_PAGAMENTO;
    }
}
