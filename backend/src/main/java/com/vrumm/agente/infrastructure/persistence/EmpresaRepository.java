package com.vrumm.agente.infrastructure.persistence;

import com.vrumm.agente.domain.model.Empresa;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface EmpresaRepository extends CrudRepository<Empresa, Long> {
    List<Empresa> findAll();
}
