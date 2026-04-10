package com.vrumm.site.interfaces.web;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@MicronautTest
class LandingControllerTest {

    @Inject
    @Client("/")
    private HttpClient httpClient;

    @Test
    void deveResponderOkNaRaiz() {
        try {
            var response = httpClient.toBlocking().exchange(HttpRequest.GET("/"), String.class);
            assertEquals(HttpStatus.OK, response.getStatus());
        } catch (HttpClientResponseException e) {
            fail("A rota GET / deve responder 200, mas retornou " + e.getStatus());
        }
    }
}
