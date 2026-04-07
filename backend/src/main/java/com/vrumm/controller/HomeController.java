package com.vrumm.controller;

import com.vrumm.domain.Cliente;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.CookieValue;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.ModelAndView;
import io.swagger.v3.oas.annotations.Hidden;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Hidden
@Controller("/home")
public class HomeController {

    private final AuthController authController;

    public HomeController(AuthController authController) {
        this.authController = authController;
    }

    @Get
    public HttpResponse<?> home(@CookieValue(value = AuthController.AUTH_COOKIE) Optional<Long> clienteId) {
        Optional<Cliente> clienteOpt = clienteId.flatMap(authController::getClienteAutenticado);

        if (clienteOpt.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }

        Map<String, Object> model = new HashMap<>();
        model.put("cliente", clienteOpt.get());
        return HttpResponse.ok(new ModelAndView<>("pages/home", model));
    }
}
