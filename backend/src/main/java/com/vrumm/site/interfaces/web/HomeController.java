package com.vrumm.site.interfaces.web;

import com.vrumm.auth.application.facade.AuthSessionFacade;
import com.vrumm.cliente.application.dto.PerfilForm;
import com.vrumm.cliente.application.facade.ClienteFacade;
import com.vrumm.cliente.domain.model.Cliente;
import com.vrumm.shared.exception.DuplicateResourceException;
import io.micronaut.http.MediaType;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.CookieValue;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.ModelAndView;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Hidden
@Controller
public class HomeController {

    private final AuthSessionFacade authSessionFacade;
    private final ClienteFacade clienteFacade;

    public HomeController(AuthSessionFacade authSessionFacade, ClienteFacade clienteFacade) {
        this.authSessionFacade = authSessionFacade;
        this.clienteFacade = clienteFacade;
    }

    @Get("/perfil")
    public HttpResponse<?> perfil(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId) {
        Optional<Cliente> clienteOpt = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (clienteOpt.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }

        Map<String, Object> model = new HashMap<>();
        model.put("cliente", clienteOpt.get());
        return HttpResponse.ok(new ModelAndView<>("pages/home", model));
    }

    @Get("/perfil/editar")
    public HttpResponse<?> editarPerfil(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                        @QueryValue Optional<String> erro,
                                        @QueryValue Optional<String> sucesso) {
        Optional<Cliente> clienteOpt = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (clienteOpt.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }

        return renderEditarPerfil(clienteOpt.get(), PerfilForm.fromCliente(clienteOpt.get()), erro.orElse(null), sucesso.orElse(null));
    }

    @Post("/perfil/editar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> atualizarPerfil(@CookieValue(value = AuthSessionFacade.AUTH_COOKIE) Optional<Long> clienteId,
                                           @Body @Valid PerfilForm form) {
        Optional<Cliente> clienteOpt = clienteId.flatMap(authSessionFacade::getClienteAutenticado);
        if (clienteOpt.isEmpty()) {
            return HttpResponse.seeOther(URI.create("/"));
        }

        try {
            Cliente atualizado = clienteFacade.atualizarPerfil(clienteOpt.get().getId(), form);
            return renderEditarPerfil(atualizado, PerfilForm.fromCliente(atualizado), null, "Perfil atualizado com sucesso");
        } catch (DuplicateResourceException | IllegalArgumentException e) {
            return renderEditarPerfil(clienteOpt.get(), form, e.getMessage(), null);
        }
    }

    @Get("/home")
    public HttpResponse<?> redirecionarHomeLegada() {
        return HttpResponse.seeOther(URI.create("/perfil"));
    }

    private HttpResponse<ModelAndView<Map<String, Object>>> renderEditarPerfil(Cliente cliente, PerfilForm form, String erro, String sucesso) {
        Map<String, Object> model = new HashMap<>();
        model.put("cliente", cliente);
        model.put("form", form);
        model.put("erro", erro);
        model.put("sucesso", sucesso);
        return HttpResponse.ok(new ModelAndView<>("pages/profile-edit", model));
    }
}
