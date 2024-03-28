package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void createData() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        roleServiceImp.save(roleAdmin);
        Role roleUser = new Role("ROLE_USER");
        roleServiceImp.save(roleUser);
        UserDTO userDTOAdmin = new UserDTO("admin", "Roman", 35,
                "Romka@gmail.com",
                "100",
                "ROLE_ADMIN");
        userServiceImp.save(userServiceImp.getUserFromUserDTO(userDTOAdmin), userDTOAdmin);
        UserDTO userDTOUser = new UserDTO("user", "Bob", 20,
                "Bob@gmail.com",
                "100",
                "ROLE_USER");
        userServiceImp.save(userServiceImp.getUserFromUserDTO(userDTOUser), userDTOUser);
    }
}