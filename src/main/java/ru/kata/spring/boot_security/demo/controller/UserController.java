package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collection;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@Controller
@RequestMapping("/admin")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public String getString(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "admin/users";
    }

    @GetMapping (value = "/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
//        model.addAttribute("roles", new Role());
        return "admin/new";
    }

//    @PostMapping()
//    public String create(@ModelAttribute("user") User user) {
//        Collection<Role> roles = user.getRoles();
//        user.setRoles(roles);
//        userService.save(user);
//        return "redirect:admin/users";
//    }

    @GetMapping(value = "/update")
    public String update(@RequestParam(value = "id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/update";
    }

    @PatchMapping
    public String edit(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/delete")
    public String delete(@RequestParam(value = "id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/delete";
    }

    @DeleteMapping(value = "/{id}")
    public String remove(@ModelAttribute("user") User user) {
        userService.deleteUser(user.getId());
        return "redirect:/admin/users";
    }
}
