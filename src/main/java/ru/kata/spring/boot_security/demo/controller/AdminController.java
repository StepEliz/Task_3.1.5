package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.*;


@Controller
@RequestMapping("/admin/users")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping
    public String getString(Model model, Principal user) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("user", userService.getUserByLogin(user.getName()));
        model.addAttribute("formUser", new User());
        model.addAttribute("roles", roleService.getAllRole());
        return "admin/users";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model,
                          Principal currentUser) {
        model.addAttribute("currentUser", userService.getUserByLogin(currentUser.getName()));
        model.addAttribute("roles", roleService.getAllRole());
        return "admin/new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveNewUser(user);
        return "redirect:/admin/users";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles") Set<Role> roles) {
        user.setRoles(roles);
        userService.update(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
