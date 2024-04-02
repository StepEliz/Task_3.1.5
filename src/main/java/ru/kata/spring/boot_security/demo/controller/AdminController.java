package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


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
        model.addAttribute("user", new UserDTO());
        Collection<Role> roles = roleService.getAllRole();
        model.addAttribute("roles", roles);
        return "admin/new";
    }

    @PostMapping(value = "/create")
    public String create(@ModelAttribute("user") UserDTO userDTO) {
        userService.save(userService.getUserFromUserDTO(userDTO), userDTO);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/update")
    public String update(@RequestParam(value = "id") int id, Model model) {
        User user = userService.getUserById(id);
        UserDTO userDTO = new UserDTO(user.getLogin(), user.getName(), user.getAge(),
                user.getEmail(), user.getPassword(), new HashSet<>(Set.of(new Role())));
        userDTO.setId((long) id);
        model.addAttribute("user", userDTO);
        Collection<Role> roles = roleService.getAllRole();
        model.addAttribute("roles", roles);
        return "admin/update";
    }

    @PatchMapping(value = "/users")
    public String edit(@ModelAttribute("user") UserDTO userDTO,
                       @ModelAttribute("roles") Collection<Role> roles) {
        userDTO.setRole(roles);
        userService.update(userDTO);
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
