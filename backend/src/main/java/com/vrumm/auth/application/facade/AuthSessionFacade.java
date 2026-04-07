package com.vrumm.auth.application.facade;

import com.vrumm.cliente.application.facade.ClienteFacade;
import com.vrumm.cliente.domain.model.Cliente;
import io.micronaut.http.cookie.Cookie;
import jakarta.inject.Singleton;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Singleton
public class AuthSessionFacade {

    public static final String AUTH_COOKIE = "vrumm_cliente_id";

    private final ClienteFacade clienteFacade;

    public AuthSessionFacade(ClienteFacade clienteFacade) {
        this.clienteFacade = clienteFacade;
    }

    public Optional<Cliente> getClienteAutenticado(Long clienteId) {
        if (clienteId == null) {
            return Optional.empty();
        }
        return clienteFacade.buscarPorId(clienteId);
    }

    public Cookie buildAuthCookie(Long clienteId) {
        return Cookie.of(AUTH_COOKIE, String.valueOf(clienteId))
                .httpOnly(true)
                .path("/")
                .maxAge((int) ChronoUnit.DAYS.getDuration().multipliedBy(7).getSeconds());
    }

    public Cookie buildExpiredAuthCookie() {
        return Cookie.of(AUTH_COOKIE, "")
                .path("/")
                .httpOnly(true)
                .maxAge(0);
    }
}
