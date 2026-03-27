package com.vrumm.service;

import com.vrumm.domain.ClienteVinculoAtivoChecker;
import jakarta.inject.Singleton;

@Singleton
public class NoOpClienteVinculoAtivoChecker implements ClienteVinculoAtivoChecker {

    @Override
    public boolean possuiVinculosAtivos(Long clienteId) {
        return false;
    }
}