package com.vrumm.pedido.application.facade;

import com.vrumm.automovel.domain.model.Automovel;
import com.vrumm.pedido.application.dto.ContratoCreditoForm;
import com.vrumm.pedido.application.dto.ContratoForm;
import com.vrumm.pedido.application.dto.FinanceiroAvaliacaoForm;
import com.vrumm.pedido.application.dto.PedidoForm;
import com.vrumm.pedido.application.dto.PedidoStatusDto;
import com.vrumm.pedido.application.query.PedidoQueryService;
import com.vrumm.pedido.application.service.PedidoDomainSupport;
import com.vrumm.pedido.application.usecase.PedidoCommandUseCase;
import com.vrumm.pedido.domain.model.PedidoAluguel;
import com.vrumm.pedido.domain.model.PedidoStatus;
import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Singleton
public class PedidoFacade {

    private final PedidoDomainSupport pedidoDomainSupport;
    private final PedidoCommandUseCase pedidoCommandUseCase;
    private final PedidoQueryService pedidoQueryService;

    public PedidoFacade(PedidoDomainSupport pedidoDomainSupport,
                        PedidoCommandUseCase pedidoCommandUseCase,
                        PedidoQueryService pedidoQueryService) {
        this.pedidoDomainSupport = pedidoDomainSupport;
        this.pedidoCommandUseCase = pedidoCommandUseCase;
        this.pedidoQueryService = pedidoQueryService;
    }

    public List<Automovel> listarAutomoveisDisponiveis() {
        return pedidoDomainSupport.listarAutomoveisDisponiveis();
    }

    public PedidoAluguel criarPedido(Long clienteId, PedidoForm form) {
        return pedidoCommandUseCase.criarPedido(clienteId, form);
    }

    public PedidoAluguel atualizarPedidoDoCliente(Long clienteId, Long pedidoId, PedidoForm form) {
        return pedidoCommandUseCase.atualizarPedidoDoCliente(clienteId, pedidoId, form);
    }

    public PedidoAluguel atualizarPedidoComoEmpresa(Long pedidoId, PedidoForm form) {
        return pedidoCommandUseCase.atualizarPedidoComoEmpresa(pedidoId, form);
    }

    public void cancelarPedidoDoCliente(Long clienteId, Long pedidoId) {
        pedidoCommandUseCase.cancelarPedidoDoCliente(clienteId, pedidoId);
    }

    public void cancelarPedidoComoEmpresa(Long pedidoId) {
        pedidoCommandUseCase.cancelarPedidoComoEmpresa(pedidoId);
    }

    public List<PedidoStatusDto> listarPedidosDoCliente(Long clienteId) {
        return pedidoQueryService.listarPedidosDoCliente(clienteId);
    }

    public List<PedidoStatusDto> listarPedidosEmpresa(PedidoStatus status) {
        return pedidoQueryService.listarPedidosGerais(status);
    }

    public List<PedidoStatusDto> listarPedidosBanco(PedidoStatus status) {
        return pedidoQueryService.listarPedidosGerais(status);
    }

    public Optional<PedidoStatusDto> buscarResumoPedidoDoCliente(Long clienteId, Long pedidoId) {
        return pedidoQueryService.buscarResumoPedidoDoCliente(clienteId, pedidoId);
    }

    public Optional<PedidoStatusDto> buscarResumoPedido(Long pedidoId) {
        return pedidoQueryService.buscarResumoPedido(pedidoId);
    }

    public Optional<PedidoForm> buscarFormularioPedidoDoCliente(Long clienteId, Long pedidoId) {
        return pedidoQueryService.buscarFormularioPedidoDoCliente(clienteId, pedidoId);
    }

    public Optional<PedidoForm> buscarFormularioPedido(Long pedidoId) {
        return pedidoQueryService.buscarFormularioPedido(pedidoId);
    }

    public void confirmarPagamentoDoCliente(Long clienteId, Long pedidoId, BigDecimal valorPago) {
        pedidoCommandUseCase.confirmarPagamentoDoCliente(clienteId, pedidoId, valorPago);
    }

    public void confirmarPagamentoComoBanco(Long pedidoId, BigDecimal valorPago) {
        pedidoCommandUseCase.confirmarPagamentoComoBanco(pedidoId, valorPago);
    }

    public void confirmarPagamentoComoEmpresa(Long pedidoId, BigDecimal valorPago) {
        pedidoCommandUseCase.confirmarPagamentoComoEmpresa(pedidoId, valorPago);
    }

    public void recusarPedidoComoEmpresa(Long pedidoId) {
        pedidoCommandUseCase.recusarPedidoComoEmpresa(pedidoId);
    }

    public void avaliarFinanceiramente(Long pedidoId, Long bancoId, FinanceiroAvaliacaoForm form) {
        pedidoCommandUseCase.avaliarFinanceiramente(pedidoId, bancoId, form);
    }

    public void salvarContrato(Long pedidoId, ContratoForm form) {
        pedidoCommandUseCase.salvarContrato(pedidoId, form);
    }

    public void salvarContratoCredito(Long pedidoId, Long bancoId, ContratoCreditoForm form) {
        pedidoCommandUseCase.salvarContratoCredito(pedidoId, bancoId, form);
    }

    public Optional<ContratoForm> buscarContratoForm(Long pedidoId) {
        return pedidoQueryService.buscarContratoForm(pedidoId);
    }

    public Optional<ContratoCreditoForm> buscarContratoCreditoForm(Long pedidoId) {
        return pedidoQueryService.buscarContratoCreditoForm(pedidoId);
    }
}
