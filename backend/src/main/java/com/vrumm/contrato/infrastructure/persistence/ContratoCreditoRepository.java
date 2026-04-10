package com.vrumm.contrato.infrastructure.persistence;

import com.vrumm.contrato.domain.model.ContratoCredito;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ContratoCreditoRepository extends CrudRepository<ContratoCredito, Long> {
    Optional<ContratoCredito> findByPedidoId(Long pedidoId);
}
