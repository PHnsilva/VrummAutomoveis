package com.vrumm.cliente.infrastructure.persistence;

import com.vrumm.cliente.domain.model.ClienteRendimento;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ClienteRendimentoRepository extends CrudRepository<ClienteRendimento, Long> {

    List<ClienteRendimento> findByClienteIdOrderByOrdemAsc(Long clienteId);

    void deleteByClienteId(Long clienteId);
}
