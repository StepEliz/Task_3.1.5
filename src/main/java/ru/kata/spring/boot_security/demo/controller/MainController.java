package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/users")
public class MainController {

    @GetMapping
    public String getString() {
        return "main";
    }
}
