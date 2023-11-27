package com.shyamalmadura.kafka.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RedirectController {
    @GetMapping(value = {"/api-docs", "/swagger-ui.html"})
    public ModelAndView redirectToApiPage() {
        return new ModelAndView("redirect:/swagger-ui/index.html");
    }
}
