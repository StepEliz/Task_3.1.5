package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {
    void save(User user, UserDTO userDTO);
    User getUserFromUserDTO(UserDTO userDTO);
    User getUserById(long id);
    void deleteUser(long id);
    void update(UserDTO userDTO);
    List<User> getUsers();
    User getUserByLogin(String login);
    void setRoleToUser(UserDTO userDTO);
    void setRoleToUserDTO(Collection<String> role, UserDTO userDTO);
}
