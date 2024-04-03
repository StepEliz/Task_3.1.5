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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private void setPasswordEncoder(PasswordEncoder passwordEncoder) {
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
        return new User(userDTO.getLogin(), userDTO.getName(), userDTO.getAge(),
                userDTO.getEmail(), userDTO.getPassword(), userDTO.getRole());
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
    public User getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(), rolToAuthority(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> rolToAuthority(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList();
    }

    @Override
    @Transactional
    public void setRoleToUser(UserDTO userDTO) {
        ArrayList<Role> roles = new ArrayList<>(roleService.getAllRole());
        ArrayList<Role> userRoles = new ArrayList<>();
        if (!userDTO.getRole().isEmpty()) {
            ArrayList<Role> userDTORole = new ArrayList<>(userDTO.getRole());
            for (Role role : roles) {
                for (Role userRole : userDTORole) {
                    if (role.toString().equals(userRole.toString())) {
                        userRoles.add(role);
                    }
                }
            }
        } else {
            userRoles.add(roles.get(2));
        }
        getUserByLogin(userDTO.getLogin()).setRoles(userRoles);
    }

    @Override
    public void setRoleToUserDTO(Collection<String> role, UserDTO userDTO) {
        List<Role> roleList = new ArrayList<>();
        for (String roleId : role) {
            Long id = Long.parseLong(roleId);
            if (roleService.getRoleById(id) != null) {
                roleList.add(roleService.getRoleById(id));
            }
        }
        userDTO.setRole(roleList);
    }
}
