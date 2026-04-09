package com.vrumm.pedido.infrastructure.persistence;

import com.vrumm.pedido.domain.model.PedidoAluguel;
import com.vrumm.pedido.domain.model.PedidoStatus;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface PedidoRepository extends CrudRepository<PedidoAluguel, Long> {

    long countByClienteId(Long clienteId);

    List<PedidoAluguel> findByClienteId(Long clienteId);

    Optional<PedidoAluguel> findByIdAndClienteId(Long id, Long clienteId);

    List<PedidoAluguel> findByStatus(PedidoStatus status);
}
