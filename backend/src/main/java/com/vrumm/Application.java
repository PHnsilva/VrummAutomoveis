package com.vrumm;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "VRUMM - Cliente API",
                version = "1.0.0",
                description = "Operações do módulo de clientes"
        ),
        tags = {
                @Tag(name = "clientes", description = "Operações do CRUD de clientes")
        }
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}