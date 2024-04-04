package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImp implements RoleService{
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Set<Role> getAllRole() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Override
    public Role getRoleById(long id) {
        return roleRepository.getById(id);
    }

    @Override
    public Set<Role> findAllById(Set<Long> s) {
        return roleRepository.findAllById(s);
    }

    @Override
    public Set<Long> getSetRoleIdBySetString(Set<String> stringSet) {
        Set<Long> setRoleId = new HashSet<>();
        for (String roleId : stringSet) {
            long id = Long.parseLong(roleId);
            if (getRoleById(id) != null) {
                setRoleId.add(id);
            }
        }
        return setRoleId;
    }
}
