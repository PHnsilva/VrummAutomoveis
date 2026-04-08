package com.vrumm.pedido.application.facade;

import com.vrumm.automovel.domain.model.Automovel;
import com.vrumm.automovel.infrastructure.persistence.AutomovelRepository;
import com.vrumm.cliente.application.facade.ClienteFacade;
import com.vrumm.pedido.application.dto.PedidoForm;
import com.vrumm.pedido.domain.model.PedidoAluguel;
import com.vrumm.pedido.domain.model.PedidoStatus;
import com.vrumm.pedido.infrastructure.persistence.PedidoRepository;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Singleton
public class PedidoFacade {

    private final PedidoRepository pedidoRepository;
    private final AutomovelRepository automovelRepository;
    private final ClienteFacade clienteFacade;

    public PedidoFacade(PedidoRepository pedidoRepository,
                        AutomovelRepository automovelRepository,
                        ClienteFacade clienteFacade) {
        this.pedidoRepository = pedidoRepository;
        this.automovelRepository = automovelRepository;
        this.clienteFacade = clienteFacade;
    }

    public List<Automovel> listarAutomoveisDisponiveis() {
        return automovelRepository.findAll().stream()
                .filter(Automovel::isDisponivel)
                .sorted(Comparator.comparing(Automovel::getMarca).thenComparing(Automovel::getModelo))
                .toList();
    }

    public PedidoAluguel criarPedido(Long clienteId, PedidoForm form) {
        validarClienteExistente(clienteId);
        Automovel automovel = automovelRepository.findById(form.getAutomovelId())
                .orElseThrow(() -> new IllegalArgumentException("Automóvel não encontrado"));

        if (!automovel.isDisponivel()) {
            throw new IllegalArgumentException("Automóvel indisponível para novo pedido");
        }

        PedidoAluguel pedido = new PedidoAluguel();
        pedido.setClienteId(clienteId);
        pedido.setAutomovelId(automovel.getId());
        pedido.setStatus(PedidoStatus.EM_ANALISE);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setObservacao(form.getObservacao());
        pedido.setValorEntrada(form.getValorEntrada());
        pedido.setPrazoMeses(form.getPrazoMeses());
        pedido.setRendaDeclarada(form.getRendaDeclarada());
        return pedidoRepository.save(pedido);
    }

    private void validarClienteExistente(Long clienteId) {
        if (clienteId == null || clienteFacade.buscarPorId(clienteId).isEmpty()) {
            throw new IllegalArgumentException("Cliente autenticado não encontrado");
        }
    }
}
