package com.vrumm.site.interfaces.web;

import com.vrumm.auth.application.facade.AuthSessionFacade;
import com.vrumm.cliente.domain.model.Cliente;
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

    private final AuthSessionFacade authSessionFacade;

    public HomeController(AuthSessionFacade authSessionFacade) {
        this.authSessionFacade = authSessionFacade;
    }

    @Get
    public HttpResponse<?> home(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId) {
        Optional<Cliente> clienteOpt = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (clienteOpt.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }

        Map<String, Object> model = new HashMap<>();
        model.put("cliente", clienteOpt.get());
        return HttpResponse.ok(new ModelAndView<>("pages/home", model));
    }
}
