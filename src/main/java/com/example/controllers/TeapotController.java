package com.example.controllers;

import com.example.errors.TeapotException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/tea")
public class TeapotController {

    @GetMapping("/coffee")
    @Operation(summary = "brew a coffee")
    public String brewCoffee() {
        throw new TeapotException(HttpStatus.I_AM_A_TEAPOT,"I am a teapot");
    }
}