package com.vrumm.pedido.application.mapper;

import com.vrumm.pedido.application.dto.PedidoForm;
import com.vrumm.pedido.domain.model.PedidoAluguel;
import jakarta.inject.Singleton;

@Singleton
public class PedidoFormMapper {

    public PedidoForm toForm(PedidoAluguel pedido) {
        return PedidoForm.fromPedido(pedido);
    }
}
