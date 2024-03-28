package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository,
                          RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void save(User user, UserDTO userDTO) {
        if (userDTO.getPassword().isEmpty()) {
            userRepository.save(user);
        } else {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(user);
        }
    }

    @Override
    public User getUserFromUserDTO(UserDTO userDTO) {
        if (userDTO.getRole().equals("ROLE_ADMIN")) {
            return new User(userDTO.getLogin(), userDTO.getName(),
                    userDTO.getAge(), userDTO.getEmail(), userDTO.getPassword(),
                    Set.of(roleService.getRoleByRoleName("ROLE_ADMIN")));
        }
        return new User(userDTO.getLogin(),userDTO.getName(),
                userDTO.getAge(), userDTO.getEmail(), userDTO.getPassword(),
                Set.of(roleService.getRoleByRoleName("ROLE_USER")));
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(UserDTO userDTO) {
        User user = getUserById(userDTO.getId());
        user.setName(userDTO.getName());
        user.setLogin(userDTO.getLogin());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        save(user, userDTO);
        if (!userDTO.getRole().isEmpty()) {
            setRoleToUser(userDTO);
        }
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.getUserByName(name);
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(), rolToAuthority(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> rolToAuthority(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).toList();
    }

    @Override
    @Transactional
    public void setRoleToUser(UserDTO userDTO) {
        if (userDTO.getRole().equals("ROLE_ADMIN")) {
            getUserByName(userDTO.getName()).setRoles(Set.of(roleService.getRoleById(1)));
        } else {
            getUserByName(userDTO.getName()).setRoles(Set.of(roleService.getRoleById(2)));
        }
    }
}
