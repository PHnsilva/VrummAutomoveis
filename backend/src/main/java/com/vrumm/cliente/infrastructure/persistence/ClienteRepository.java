package com.vrumm.cliente.infrastructure.persistence;

import com.vrumm.cliente.domain.model.Cliente;
import com.vrumm.cliente.domain.model.Cpf;
import com.vrumm.cliente.domain.model.PerfilAcesso;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    List<Cliente> findAll();
    List<Cliente> findByPerfil(PerfilAcesso perfil);
    Optional<Cliente> findByCpf(Cpf cpf);
    Optional<Cliente> findByEmail(String email);
}
