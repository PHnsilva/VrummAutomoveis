package com.vrumm.auth.application.dto;

import com.vrumm.cliente.application.dto.ClienteResponse;
import com.vrumm.cliente.domain.model.Cliente;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class AuthResponse {

    private boolean autenticado;
    private String mensagem;
    private ClienteResponse cliente;

    public static AuthResponse fromCliente(Cliente cliente, boolean autenticado, String mensagem) {
        AuthResponse response = new AuthResponse();
        response.setAutenticado(autenticado);
        response.setMensagem(mensagem);
        response.setCliente(ClienteResponse.fromCliente(cliente));
        return response;
    }

    public boolean isAutenticado() { return autenticado; }
    public void setAutenticado(boolean autenticado) { this.autenticado = autenticado; }
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public ClienteResponse getCliente() { return cliente; }
    public void setCliente(ClienteResponse cliente) { this.cliente = cliente; }
}
