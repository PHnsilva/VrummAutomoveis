# Backend Vrumm Automóveis

Aplicação Micronaut 4.10.10 em Java 21, com páginas Thymeleaf, API REST, PostgreSQL e migrações Flyway. O [README principal](../README.md) descreve o domínio, os fluxos e as limitações atuais da autenticação.

## Execução e validação

Dentro de `backend/`, com JDK 21 e PostgreSQL configurado:

```bash
./mvnw mn:run
./mvnw test
./mvnw clean package
```

No PowerShell, use `.\mvnw.bat` em lugar de `./mvnw`. Alternativamente, `docker compose up --build` inicia banco e aplicação com os valores locais do Compose. As portas padrão são 5432 e 8080.

Configure `DATASOURCES_DEFAULT_URL`, `DATASOURCES_DEFAULT_USERNAME` e `DATASOURCES_DEFAULT_PASSWORD` conforme sua instância. Os padrões estão em [application.properties](src/main/resources/application.properties). O Swagger fica em `/swagger-ui/` e as migrações em [db/migration/](src/main/resources/db/migration/).

Os testes atuais verificam a inicialização e a landing page, sem cobrir os fluxos completos de negócio. As credenciais do Compose são de demonstração local.

## Referências do framework

## Micronaut 4.10.10 Documentation

- [User Guide](https://docs.micronaut.io/4.10.10/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.10.10/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.10.10/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

---

- [Micronaut Maven Plugin documentation](https://micronaut-projects.github.io/micronaut-maven-plugin/latest/)

## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)

## Feature flyway documentation

- [Micronaut Flyway Database Migration documentation](https://micronaut-projects.github.io/micronaut-flyway/latest/guide/index.html)

- [https://flywaydb.org/](https://flywaydb.org/)

## Feature jdbc-hikari documentation

- [Micronaut Hikari JDBC Connection Pool documentation](https://micronaut-projects.github.io/micronaut-sql/latest/guide/index.html#jdbc)

## Feature maven-enforcer-plugin documentation

- [https://maven.apache.org/enforcer/maven-enforcer-plugin/](https://maven.apache.org/enforcer/maven-enforcer-plugin/)

## Feature data-jdbc documentation

- [Micronaut Data JDBC documentation](https://micronaut-projects.github.io/micronaut-data/latest/guide/index.html#jdbc)

## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)

## Feature test-resources documentation

- [Micronaut Test Resources documentation](https://micronaut-projects.github.io/micronaut-test-resources/latest/guide/)

## Feature views-thymeleaf documentation

- [Micronaut Thymeleaf Views documentation](https://micronaut-projects.github.io/micronaut-views/latest/guide/index.html#thymeleaf)

- [https://www.thymeleaf.org/](https://www.thymeleaf.org/)

## Feature validation documentation

- [Micronaut Validation documentation](https://micronaut-projects.github.io/micronaut-validation/latest/guide/)
