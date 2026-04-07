package com.vrumm.cliente.infrastructure.service;

import com.vrumm.cliente.domain.model.ClienteVinculoAtivoChecker;
import jakarta.inject.Singleton;

@Singleton
public class NoOpClienteVinculoAtivoChecker implements ClienteVinculoAtivoChecker {

    @Override
    public boolean possuiVinculosAtivos(Long clienteId) {
        return false;
    }
}
