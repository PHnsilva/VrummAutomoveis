package com.vrumm.pedido.application.service;

import com.vrumm.agente.infrastructure.persistence.BancoRepository;
import com.vrumm.automovel.domain.model.Automovel;
import com.vrumm.automovel.infrastructure.persistence.AutomovelRepository;
import com.vrumm.cliente.application.facade.ClienteFacade;
import com.vrumm.pedido.domain.model.PedidoAluguel;
import com.vrumm.pedido.infrastructure.persistence.PedidoRepository;
import jakarta.inject.Singleton;

import java.util.Comparator;
import java.util.List;

@Singleton
public class PedidoDomainSupport {

    private final PedidoRepository pedidoRepository;
    private final AutomovelRepository automovelRepository;
    private final ClienteFacade clienteFacade;
    private final BancoRepository bancoRepository;

    public PedidoDomainSupport(PedidoRepository pedidoRepository,
                               AutomovelRepository automovelRepository,
                               ClienteFacade clienteFacade,
                               BancoRepository bancoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.automovelRepository = automovelRepository;
        this.clienteFacade = clienteFacade;
        this.bancoRepository = bancoRepository;
    }

    public List<Automovel> listarAutomoveisDisponiveis() {
        return automovelRepository.findAll().stream()
                .filter(Automovel::isDisponivel)
                .sorted(Comparator.comparing(Automovel::getMarca).thenComparing(Automovel::getModelo))
                .toList();
    }

    public void validarClienteExistente(Long clienteId) {
        if (clienteId == null || clienteFacade.buscarPorId(clienteId).isEmpty()) {
            throw new IllegalArgumentException("Cliente autenticado não encontrado");
        }
    }

    public PedidoAluguel buscarPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
    }

    public PedidoAluguel buscarPedidoDoCliente(Long clienteId, Long pedidoId) {
        validarClienteExistente(clienteId);
        return pedidoRepository.findByIdAndClienteId(pedidoId, clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
    }

    public Automovel buscarAutomovel(Long automovelId) {
        return automovelRepository.findById(automovelId)
                .orElseThrow(() -> new IllegalArgumentException("Automóvel não encontrado"));
    }

    public Automovel buscarAutomovelDisponivelParaNovoPedido(Long automovelId) {
        Automovel automovel = buscarAutomovel(automovelId);
        if (!automovel.isDisponivel()) {
            throw new IllegalArgumentException("Automóvel indisponível para novo pedido");
        }
        return automovel;
    }

    public Automovel buscarAutomovelDisponivelParaEdicao(PedidoAluguel pedido, Long automovelId) {
        Automovel automovel = buscarAutomovel(automovelId);
        boolean indisponivelParaTroca = !automovel.isDisponivel() && !automovel.getId().equals(pedido.getAutomovelId());
        if (indisponivelParaTroca) {
            throw new IllegalArgumentException("Automóvel indisponível para o pedido");
        }
        return automovel;
    }

    public String buscarDescricaoAutomovel(Long automovelId) {
        return automovelRepository.findById(automovelId)
                .map(Automovel::getDescricaoExibicao)
                .orElse("Automóvel #" + automovelId);
    }

    public String buscarNomeBanco(Long bancoId) {
        if (bancoId == null) {
            return null;
        }
        return bancoRepository.findById(bancoId)
                .map(banco -> banco.getNome() + " (" + banco.getCodigo() + ")")
                .orElse("Banco #" + bancoId);
    }
}
