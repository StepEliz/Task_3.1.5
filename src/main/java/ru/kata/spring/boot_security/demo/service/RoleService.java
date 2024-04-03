package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.Collection;

public interface RoleService {
    void save(Role role);
    Collection<Role> getAllRole();
    Role getRoleById(Long id);
}
