package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.annotation.PostConstruct;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserServiceImp userServiceImp;

    public WebSecurityConfig(SuccessUserHandler successUserHandler,
                             UserServiceImp userServiceImp) {
        this.successUserHandler = successUserHandler;
        this.userServiceImp = userServiceImp;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index").authenticated()
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
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
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userServiceImp);
        return authenticationProvider;
    }

//    Пароль у пальзователей - 100
    @PostConstruct
    public void createData() {
        User admin = new User("admin", "Roman", 35,
                "Romka@gmail.com",
                "$2a$12$qAfnSqp92AVJOs9ot3DZ/erzfpzAfEhzW8Oqbp0AoNupFlADGeOq6",
                Set.of(new Role("ROLE_ADMIN")));
        userServiceImp.save(admin);

        User user = new User("user", "Bob", 20,
                "Bob@gmail.com",
                "$2a$12$qAfnSqp92AVJOs9ot3DZ/erzfpzAfEhzW8Oqbp0AoNupFlADGeOq6",
                Set.of(new Role("ROLE_USER")));
        userServiceImp.save(user);
    }
}