package com.vrumm.automovel.infrastructure.persistence;

import com.vrumm.automovel.domain.model.Automovel;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface AutomovelRepository extends CrudRepository<Automovel, Long> {

    List<Automovel> findAll();
}
