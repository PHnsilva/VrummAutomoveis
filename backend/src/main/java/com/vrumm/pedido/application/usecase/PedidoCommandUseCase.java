package com.vrumm.pedido.application.usecase;

import com.vrumm.contrato.domain.model.Contrato;
import com.vrumm.contrato.domain.model.ContratoCredito;
import com.vrumm.contrato.infrastructure.persistence.ContratoCreditoRepository;
import com.vrumm.contrato.infrastructure.persistence.ContratoRepository;
import com.vrumm.pedido.application.dto.ContratoCreditoForm;
import com.vrumm.pedido.application.dto.ContratoForm;
import com.vrumm.pedido.application.dto.FinanceiroAvaliacaoForm;
import com.vrumm.pedido.application.dto.PedidoForm;
import com.vrumm.pedido.application.service.PedidoDomainSupport;
import com.vrumm.pedido.domain.model.PedidoAluguel;
import com.vrumm.pedido.domain.model.PedidoStatus;
import com.vrumm.pedido.infrastructure.persistence.PedidoRepository;
import jakarta.inject.Singleton;

import java.math.BigDecimal;

@Singleton
public class PedidoCommandUseCase {

    private final PedidoRepository pedidoRepository;
    private final ContratoRepository contratoRepository;
    private final ContratoCreditoRepository contratoCreditoRepository;
    private final PedidoDomainSupport pedidoDomainSupport;

    public PedidoCommandUseCase(PedidoRepository pedidoRepository,
                                ContratoRepository contratoRepository,
                                ContratoCreditoRepository contratoCreditoRepository,
                                PedidoDomainSupport pedidoDomainSupport) {
        this.pedidoRepository = pedidoRepository;
        this.contratoRepository = contratoRepository;
        this.contratoCreditoRepository = contratoCreditoRepository;
        this.pedidoDomainSupport = pedidoDomainSupport;
    }

    public PedidoAluguel criarPedido(Long clienteId, PedidoForm form) {
        pedidoDomainSupport.validarClienteExistente(clienteId);
        Long automovelId = pedidoDomainSupport.buscarAutomovelDisponivelParaNovoPedido(form.getAutomovelId()).getId();
        PedidoAluguel pedido = PedidoAluguel.criarSolicitacao(
                clienteId,
                automovelId,
                form.getObservacao(),
                form.getValorEntradaBigDecimal(),
                form.getPrazoMesesInteger(),
                form.getRendaDeclaradaBigDecimal()
        );
        return pedidoRepository.save(pedido);
    }

    public PedidoAluguel atualizarPedidoDoCliente(Long clienteId, Long pedidoId, PedidoForm form) {
        return atualizarPedido(pedidoDomainSupport.buscarPedidoDoCliente(clienteId, pedidoId), form);
    }

    public PedidoAluguel atualizarPedidoComoEmpresa(Long pedidoId, PedidoForm form) {
        return atualizarPedido(pedidoDomainSupport.buscarPedido(pedidoId), form);
    }

    public void cancelarPedidoDoCliente(Long clienteId, Long pedidoId) {
        cancelarPedido(pedidoDomainSupport.buscarPedidoDoCliente(clienteId, pedidoId));
    }

    public void cancelarPedidoComoEmpresa(Long pedidoId) {
        cancelarPedido(pedidoDomainSupport.buscarPedido(pedidoId));
    }

    public void confirmarPagamentoDoCliente(Long clienteId, Long pedidoId, BigDecimal valorPago) {
        confirmarPagamento(pedidoDomainSupport.buscarPedidoDoCliente(clienteId, pedidoId), valorPago);
    }

    public void confirmarPagamentoComoBanco(Long pedidoId, BigDecimal valorPago) {
        confirmarPagamento(pedidoDomainSupport.buscarPedido(pedidoId), valorPago);
    }

    public void confirmarPagamentoComoEmpresa(Long pedidoId, BigDecimal valorPago) {
        confirmarPagamento(pedidoDomainSupport.buscarPedido(pedidoId), valorPago);
    }

    public void recusarPedidoComoEmpresa(Long pedidoId) {
        PedidoAluguel pedido = pedidoDomainSupport.buscarPedido(pedidoId);
        pedido.recusar();
        pedidoRepository.update(pedido);
    }

    public void avaliarFinanceiramente(Long pedidoId, Long bancoId, FinanceiroAvaliacaoForm form) {
        PedidoAluguel pedido = pedidoDomainSupport.buscarPedido(pedidoId);
        pedido.registrarParecerFinanceiro(bancoId, Boolean.TRUE.equals(form.getFavoravel()), form.getObservacao());
        pedidoRepository.update(pedido);
    }

    public void salvarContrato(Long pedidoId, ContratoForm form) {
        PedidoAluguel pedido = pedidoDomainSupport.buscarPedido(pedidoId);
        validarPedidoParaContrato(pedido);
        Contrato contrato = contratoRepository.findByPedidoId(pedidoId).orElseGet(Contrato::new);
        if (contrato.getId() == null) {
            contrato.prepararNovoRegistro(pedidoId, form.getTipoContrato(), form.getTipoProprietario(), form.getObservacao());
            contratoRepository.save(contrato);
            return;
        }
        contrato.setTipoContrato(form.getTipoContrato());
        contrato.setTipoProprietario(form.getTipoProprietario());
        contrato.setObservacao(form.getObservacao());
        contratoRepository.update(contrato);
    }

    public void salvarContratoCredito(Long pedidoId, Long bancoId, ContratoCreditoForm form) {
        PedidoAluguel pedido = pedidoDomainSupport.buscarPedido(pedidoId);
        validarPedidoParaContratoCredito(pedido);
        ContratoCredito contratoCredito = contratoCreditoRepository.findByPedidoId(pedidoId).orElseGet(ContratoCredito::new);
        if (contratoCredito.getId() == null) {
            contratoCredito.prepararNovoRegistro(pedidoId, bancoId, form.getNumeroReferencia(), form.getValorCredito(), form.getObservacao());
            contratoCreditoRepository.save(contratoCredito);
            return;
        }
        contratoCredito.setBancoId(bancoId);
        contratoCredito.setNumeroReferencia(form.getNumeroReferencia());
        contratoCredito.setValorCredito(form.getValorCredito());
        contratoCredito.setObservacao(form.getObservacao());
        contratoCreditoRepository.update(contratoCredito);
    }

    private PedidoAluguel atualizarPedido(PedidoAluguel pedido, PedidoForm form) {
        Long automovelId = pedidoDomainSupport.buscarAutomovelDisponivelParaEdicao(pedido, form.getAutomovelId()).getId();
        pedido.atualizarSolicitacao(
                automovelId,
                form.getObservacao(),
                form.getValorEntradaBigDecimal(),
                form.getPrazoMesesInteger(),
                form.getRendaDeclaradaBigDecimal()
        );
        return pedidoRepository.update(pedido);
    }

    private void cancelarPedido(PedidoAluguel pedido) {
        pedido.cancelar();
        pedidoRepository.update(pedido);
    }

    private void confirmarPagamento(PedidoAluguel pedido, BigDecimal valorPago) {
        pedido.finalizar(valorPago);
        pedidoRepository.update(pedido);
    }

    private void validarPedidoParaContrato(PedidoAluguel pedido) {
        if (pedido.isEncerrado() && pedido.getStatus() != PedidoStatus.FINALIZADO) {
            throw new IllegalStateException("Contrato só pode ser gerenciado em pedidos ativos ou finalizados");
        }
    }

    private void validarPedidoParaContratoCredito(PedidoAluguel pedido) {
        if (pedido.getParecerFinanceiroFavoravel() == null || !pedido.getParecerFinanceiroFavoravel()) {
            throw new IllegalStateException("Contrato de crédito só pode ser registrado após parecer financeiro favorável");
        }
    }
}
