package com.vrumm.pedido.application.query;

import com.vrumm.contrato.infrastructure.persistence.ContratoCreditoRepository;
import com.vrumm.contrato.infrastructure.persistence.ContratoRepository;
import com.vrumm.pedido.application.dto.ContratoCreditoForm;
import com.vrumm.pedido.application.dto.ContratoForm;
import com.vrumm.pedido.application.dto.PedidoForm;
import com.vrumm.pedido.application.dto.PedidoStatusDto;
import com.vrumm.pedido.application.mapper.PedidoFormMapper;
import com.vrumm.pedido.application.mapper.PedidoStatusMapper;
import com.vrumm.pedido.application.service.PedidoDomainSupport;
import com.vrumm.pedido.domain.model.PedidoAluguel;
import com.vrumm.pedido.domain.model.PedidoStatus;
import com.vrumm.pedido.infrastructure.persistence.PedidoRepository;
import jakarta.inject.Singleton;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Singleton
public class PedidoQueryService {

    private final PedidoRepository pedidoRepository;
    private final ContratoRepository contratoRepository;
    private final ContratoCreditoRepository contratoCreditoRepository;
    private final PedidoDomainSupport pedidoDomainSupport;
    private final PedidoStatusMapper pedidoStatusMapper;
    private final PedidoFormMapper pedidoFormMapper;

    public PedidoQueryService(PedidoRepository pedidoRepository,
                              ContratoRepository contratoRepository,
                              ContratoCreditoRepository contratoCreditoRepository,
                              PedidoDomainSupport pedidoDomainSupport,
                              PedidoStatusMapper pedidoStatusMapper,
                              PedidoFormMapper pedidoFormMapper) {
        this.pedidoRepository = pedidoRepository;
        this.contratoRepository = contratoRepository;
        this.contratoCreditoRepository = contratoCreditoRepository;
        this.pedidoDomainSupport = pedidoDomainSupport;
        this.pedidoStatusMapper = pedidoStatusMapper;
        this.pedidoFormMapper = pedidoFormMapper;
    }

    public List<PedidoStatusDto> listarPedidosDoCliente(Long clienteId) {
        pedidoDomainSupport.validarClienteExistente(clienteId);
        return pedidoRepository.findByClienteId(clienteId).stream()
                .sorted(Comparator.comparing(PedidoAluguel::getDataCriacao).reversed())
                .map(pedidoStatusMapper::toDto)
                .toList();
    }

    public List<PedidoStatusDto> listarPedidosGerais(PedidoStatus status) {
        return buscarPedidos(status).stream()
                .sorted(Comparator.comparing(PedidoAluguel::getDataCriacao).reversed())
                .map(pedidoStatusMapper::toDto)
                .toList();
    }

    public Optional<PedidoStatusDto> buscarResumoPedidoDoCliente(Long clienteId, Long pedidoId) {
        pedidoDomainSupport.validarClienteExistente(clienteId);
        return pedidoRepository.findByIdAndClienteId(pedidoId, clienteId).map(pedidoStatusMapper::toDto);
    }

    public Optional<PedidoStatusDto> buscarResumoPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).map(pedidoStatusMapper::toDto);
    }

    public Optional<PedidoForm> buscarFormularioPedidoDoCliente(Long clienteId, Long pedidoId) {
        pedidoDomainSupport.validarClienteExistente(clienteId);
        return pedidoRepository.findByIdAndClienteId(pedidoId, clienteId).map(pedidoFormMapper::toForm);
    }

    public Optional<PedidoForm> buscarFormularioPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).map(pedidoFormMapper::toForm);
    }

    public Optional<ContratoForm> buscarContratoForm(Long pedidoId) {
        return contratoRepository.findByPedidoId(pedidoId).map(contrato -> {
            ContratoForm form = new ContratoForm();
            form.setTipoContrato(contrato.getTipoContrato());
            form.setTipoProprietario(contrato.getTipoProprietario());
            form.setObservacao(contrato.getObservacao());
            return form;
        });
    }

    public Optional<ContratoCreditoForm> buscarContratoCreditoForm(Long pedidoId) {
        return contratoCreditoRepository.findByPedidoId(pedidoId).map(contrato -> {
            ContratoCreditoForm form = new ContratoCreditoForm();
            form.setNumeroReferencia(contrato.getNumeroReferencia());
            form.setValorCredito(contrato.getValorCredito());
            form.setObservacao(contrato.getObservacao());
            return form;
        });
    }

    private List<PedidoAluguel> buscarPedidos(PedidoStatus status) {
        if (status != null) {
            return pedidoRepository.findByStatus(status);
        }
        return StreamSupport.stream(pedidoRepository.findAll().spliterator(), false).toList();
    }
}
