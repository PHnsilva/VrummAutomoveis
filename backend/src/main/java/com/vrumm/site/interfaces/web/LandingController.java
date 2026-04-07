package com.vrumm.site.interfaces.web;

import com.vrumm.auth.application.facade.AuthSessionFacade;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.CookieValue;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.ModelAndView;
import io.swagger.v3.oas.annotations.Hidden;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Hidden
@Controller("/")
public class LandingController {

    private final AuthSessionFacade authSessionFacade;

    public LandingController(AuthSessionFacade authSessionFacade) {
        this.authSessionFacade = authSessionFacade;
    }

    @Get
    public HttpResponse<?> index(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                 @QueryValue Optional<String> erro) {
        if (clienteId.isPresent() && authSessionFacade.getClienteAutenticado(clienteId.get()).isPresent()) {
            return HttpResponse.seeOther(URI.create("/home"));
        }

        Map<String, Object> model = new HashMap<>();
        model.put("erroLogin", erro.filter("login"::equals).isPresent());
        model.put("erroCadastro", erro.filter("cadastro"::equals).isPresent());
        return HttpResponse.ok(new ModelAndView<>("pages/landing", model));
    }
}
