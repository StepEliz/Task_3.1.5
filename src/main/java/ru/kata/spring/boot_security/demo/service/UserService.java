package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entity.User;
import java.util.List;

public interface UserService {
    void save(User user, UserDTO userDTO);
    User getUserFromUserDTO(UserDTO userDTO);
    User getUserById(long id);
    void deleteUser(long id);
    void update(UserDTO userDTO);
    List<User> getUsers();
    User getUserByName(String name);
    User getUserByLogin(String login);
    void setRoleToUser(UserDTO userDTO);
}
