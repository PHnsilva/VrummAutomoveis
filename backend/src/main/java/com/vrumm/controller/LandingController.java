package com.vrumm.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.ModelAndView;

@Controller("/")
public class LandingController {

    @Get
    public ModelAndView<?> index() {
        return new ModelAndView<>("pages/landing", null);
    }
}