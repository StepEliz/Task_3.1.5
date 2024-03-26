package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue
    private Long id;
    private String role;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private Set<User> users = new HashSet<>();

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public String getRoleName() {
        return role;
    }
    @Override
    public String getAuthority() {
        return null;
    }
}
