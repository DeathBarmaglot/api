package com.rest.api.article.service;

import com.rest.api.article.dao.UserDao;
import com.rest.api.article.entity.Role;
import com.rest.api.article.entity.User;
import com.rest.api.article.repository.RoleRepository;
import com.rest.api.article.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDao {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        log.info("Saving user by name {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving role by name {} to the database", role.getRoleName());
        return roleRepository.save(role);
    }

    @Override
    public void allRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRoleName(roleName);
        log.info("Saving role {} into user by name {} to the database", role.getRoleName(), user.getUsername());
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("Searching user by name {} to the database", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void removeUser(User user) {
        userRepository.delete(user);
    }
}
