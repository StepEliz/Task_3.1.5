package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.*;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/users")
    public String getString(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "admin/users";
    }

    @GetMapping (value = "/new")
    public String newUser(Model model) {
        model.addAttribute("roles", new ArrayList<>(roleService.getAllRole()));
        model.addAttribute("user", new User());
        return "admin/new";
    }

    @PostMapping(value = "/create")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "roles") Set<Role> roles) {
        user.setRoles(roles);
        userService.saveNewUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/update")
    public String update(@RequestParam(value = "id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRole());
        return "admin/update";
    }

    @PatchMapping(value = "/users")
    public String edit(@ModelAttribute("user") User user,
                       @RequestParam(value = "roles") Set<Role> roles) {
        user.setRoles(roles);
        userService.update(user);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/delete")
    public String delete(@RequestParam(value = "id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/delete";
    }

    @DeleteMapping(value = "/{id}")
    public String remove(@ModelAttribute("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
