package com.vrumm.controller.app;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.ModelAndView;

@Controller("/home")
public class HomeController {

    @Get
    public ModelAndView<?> home() {
        return new ModelAndView<>("pages/home", null);
    }
}