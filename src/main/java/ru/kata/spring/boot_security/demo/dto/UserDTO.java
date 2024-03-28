package ru.kata.spring.boot_security.demo.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String login;
    private String password;
    private String name;
    private int age;
    private String email;
    private String role;

    public UserDTO(){}

    public UserDTO(String login, String name, int age, String email, String password, String role) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
