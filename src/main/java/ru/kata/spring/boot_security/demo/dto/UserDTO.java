package ru.kata.spring.boot_security.demo.dto;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.Collection;
import java.util.List;

public class UserDTO {
    private Long id;
    private String login;
    private String password;
    private String name;
    private int age;
    private String email;
    private List<Role> role;

    public UserDTO(){}

    public UserDTO(String login, String name, int age, String email, String password, List<Role> role) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }
}
