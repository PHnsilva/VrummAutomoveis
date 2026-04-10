package com.vrumm.cliente.application.mapper;

import com.vrumm.cliente.application.dto.ClienteForm;
import com.vrumm.cliente.application.dto.ClienteResponse;
import com.vrumm.cliente.domain.model.Cliente;

public final class ClienteMapper {
    private ClienteMapper() {}

    public static ClienteForm toForm(Cliente cliente) {
        ClienteForm form = new ClienteForm();
        form.setNome(cliente.getNome());
        form.setEmail(cliente.getEmail());
        form.setCpf(cliente.getCpf().getValor());
        form.setRg(cliente.getRg());
        form.setProfissao(cliente.getProfissao());
        return form;
    }

    public static ClienteResponse toResponse(Cliente cliente) {
        ClienteResponse response = new ClienteResponse();
        response.setId(cliente.getId());
        response.setNome(cliente.getNome());
        response.setEmail(cliente.getEmail());
        response.setCpf(cliente.getCpf().getValor());
        response.setRg(cliente.getRg());
        response.setProfissao(cliente.getProfissao());
        response.setPerfil(cliente.getPerfil().name());
        return response;
    }
}
