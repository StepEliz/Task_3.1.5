package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class RestUserController {
    UserService userService;
    public RestUserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/getUser")
    public User getUser(Principal principal) {
        return userService.getUserByLogin(principal.getName());
    }
}
