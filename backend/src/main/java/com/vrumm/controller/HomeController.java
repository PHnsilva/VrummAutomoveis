package com.vrumm.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

import java.util.Map;

@Controller
public class HomeController {

    @Get("/")
    @View("home/index")
    public Map<String, Object> index() {
        return Map.of(
            "title", "Vrumm Automóveis",
            "subtitle", "Sistema de Aluguel de Carros"
        );
    }
}