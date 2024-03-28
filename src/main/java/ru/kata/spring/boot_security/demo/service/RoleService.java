package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.Role;

@Service
public interface RoleService {
    void save(Role role);
    Role getRoleById(long id);
    Role getRoleByRoleName(String name);
}
