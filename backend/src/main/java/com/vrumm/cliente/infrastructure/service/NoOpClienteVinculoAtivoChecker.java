package com.vrumm.cliente.infrastructure.service;

import com.vrumm.cliente.domain.model.ClienteVinculoAtivoChecker;
import com.vrumm.pedido.infrastructure.persistence.PedidoRepository;
import jakarta.inject.Singleton;

@Singleton
public class NoOpClienteVinculoAtivoChecker implements ClienteVinculoAtivoChecker {

    private final PedidoRepository pedidoRepository;

    public NoOpClienteVinculoAtivoChecker(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public boolean possuiVinculosAtivos(Long clienteId) {
        return pedidoRepository.countByClienteId(clienteId) > 0;
    }
}
