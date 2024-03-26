package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserServiceImp userServiceImp;
    private final RoleServiceImp roleServiceImp;
    public WebSecurityConfig(SuccessUserHandler successUserHandler,
                             UserServiceImp userServiceImp,
                             RoleServiceImp roleServiceImp) {
        this.successUserHandler = successUserHandler;
        this.userServiceImp = userServiceImp;
        this.roleServiceImp = roleServiceImp;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index").authenticated()
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
//                .antMatchers("/admin/users").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userServiceImp);
        return authenticationProvider;
    }

//    Пароль у пальзователей - 100
    @PostConstruct
    public void createData() {
        User admin = new User("admin", "admin", 35,
                "Admin@gmail.com",
                "$2a$12$qAfnSqp92AVJOs9ot3DZ/erzfpzAfEhzW8Oqbp0AoNupFlADGeOq6");
        Role adminRole = new Role("ROLE_ADMIN");
        admin.setRoles(Set.of(adminRole));
        userServiceImp.save(admin);

        User user = new User("user", "user", 20,
                "User@gmail.com",
                "$2a$12$qAfnSqp92AVJOs9ot3DZ/erzfpzAfEhzW8Oqbp0AoNupFlADGeOq6");
        userServiceImp.save(user);
        Role userRole = new Role("ROLE_USER");
        user.setRoles(Set.of(userRole));
        userServiceImp.save(user);
    }
}