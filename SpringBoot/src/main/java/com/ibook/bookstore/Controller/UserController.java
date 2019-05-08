package com.ibook.bookstore.Controller;

import com.ibook.bookstore.Entity.User;
import com.ibook.bookstore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/user/{name}")
    public User getUserByName(@PathVariable("name") String name) {
        return userService.findUserByName(name);
    }
}
