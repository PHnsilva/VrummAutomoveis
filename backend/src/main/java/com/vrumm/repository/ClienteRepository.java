package com.vrumm.repository;

import com.vrumm.domain.Cliente;
import com.vrumm.domain.Cpf;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

    List<Cliente> findAll();

    Optional<Cliente> findByCpf(Cpf cpf);

    Optional<Cliente> findByEmail(String email);
}
