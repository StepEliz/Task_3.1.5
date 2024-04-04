package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {
    void saveNewUser(User user);
    void update(User user);
    User getUserById(long id);
    void deleteUser(long id);
    List<User> getUsers();
    User getUserByLogin(String login);
}
