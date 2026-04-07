package com.vrumm;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "VRUMM Automóveis API",
                version = "1.0.0",
                description = "Fluxos de autenticação e CRUD de clientes da aplicação VRUMM"
        ),
        tags = {
                @Tag(name = "auth", description = "Operações de autenticação"),
                @Tag(name = "clientes", description = "Operações do CRUD de clientes")
        }
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}