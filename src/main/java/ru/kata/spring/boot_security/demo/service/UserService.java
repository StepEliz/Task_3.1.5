package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    void saveNewUser(User user);
    void update(User user);
    User getUserById(long id);
    void deleteUser(long id);
    List<User> getUsers();
    User getUserByLogin(String login);
    void setRoleToUser(User user, Set<String> role);
}
