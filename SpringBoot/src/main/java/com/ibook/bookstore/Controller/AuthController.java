package com.ibook.bookstore.Controller;

import com.ibook.bookstore.Dao.UserDao;
import com.ibook.bookstore.Service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/authenticate")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String authenticate(HttpServletRequest request, HttpServletResponse response) {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();

        return "Unauthorized";
    }

    @GetMapping("/api/public/name/verify")
    public Boolean verifyName(@RequestParam("name") String name) {
        return userService.nameCanBeRegistered(name);
    }

    @GetMapping("/api/public/email/verify")
    public Boolean verifyEmail(@RequestParam("email") String email) {
        return userService.emailCanBeRegistered(email);
    }
}
