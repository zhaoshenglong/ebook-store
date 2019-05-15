package com.ibook.bookstore.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {
    @RequestMapping(value = "/api/authenticate")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String authenticate(HttpServletRequest request, HttpServletResponse response) {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();

        return "Unauthorized";
    }
}
