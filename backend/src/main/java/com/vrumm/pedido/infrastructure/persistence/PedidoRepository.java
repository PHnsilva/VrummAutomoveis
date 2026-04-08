package com.vrumm.pedido.infrastructure.persistence;

import com.vrumm.pedido.domain.model.PedidoAluguel;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface PedidoRepository extends CrudRepository<PedidoAluguel, Long> {

    long countByClienteId(Long clienteId);
}
