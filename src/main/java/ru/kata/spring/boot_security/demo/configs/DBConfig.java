package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DBConfig {
    private final RoleService roleService;
    private final UserService userService;

    DBConfig (RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void createData() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        roleService.save(roleAdmin);
        Role roleUser = new Role("ROLE_USER");
        roleService.save(roleUser);
        List<Role> roles = new ArrayList<>();
        roles.add(roleAdmin);
        UserDTO userDTOAdmin = new UserDTO("admin", "Roman", 35,
                "Romka@gmail.com",
                "100",
                roles);
        userService.save(userService.getUserFromUserDTO(userDTOAdmin), userDTOAdmin);
        roles.clear();
        roles.add(roleUser);
        UserDTO userDTOUser = new UserDTO("user", "Bob", 20,
                "Bob@gmail.com",
                "100",
                roles);
        userService.save(userService.getUserFromUserDTO(userDTOUser), userDTOUser);
    }
}
