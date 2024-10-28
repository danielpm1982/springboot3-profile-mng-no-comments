package com.danielpm1982.springboot3_profile_mng_no_comments.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(
        value = {"/", "/home", "/index"},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE}
)
public class HomeController {
    @Value("${welcome.message:Welcome to Profile Manager REST API !}")
    private String WELCOME_MESSAGE;

    @GetMapping("")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    String showWelcomeMessage() {
        return WELCOME_MESSAGE;
    }
}
