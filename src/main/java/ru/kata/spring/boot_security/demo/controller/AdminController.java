package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public String getString(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "admin/users";
    }

    @GetMapping (value = "/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "admin/new";
    }

    @PostMapping(value = "/create")
    public String create(@ModelAttribute("user") UserDTO userDTO) {
        userService.save(userService.getUserFromUserDTO(userDTO));
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/update")
    public String update(@RequestParam(value = "id") int id, Model model) {
        UserDTO userDTO = new UserDTO(userService.getUserById(id).getLogin(),
                userService.getUserById(id).getName(),
                userService.getUserById(id).getAge(),
                userService.getUserById(id).getEmail(),
                userService.getUserById(id).getPassword(),
                "");
        userDTO.setId((long) id);
        model.addAttribute("user", userDTO);
        return "admin/update";
    }

    @PatchMapping(value = "/users")
    public String edit(@ModelAttribute("user") UserDTO userDTO) {
        userService.update(userDTO);
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
