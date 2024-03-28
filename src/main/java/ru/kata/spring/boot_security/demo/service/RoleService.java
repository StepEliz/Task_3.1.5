package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.Role;

public interface RoleService {
    void save(Role role);
    Role getRoleById(long id);
    Role getRoleByRoleName(String name);
}
