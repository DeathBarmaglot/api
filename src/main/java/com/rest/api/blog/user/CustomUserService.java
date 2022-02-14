package com.rest.api.blog.user;

import com.rest.api.blog.role.Role;
import com.rest.api.blog.role.RoleRepository;
import com.rest.api.blog.utils.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public String saveUser(User user) {
        log.info("Saving user by name {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = userRepository.save(user);
        return saved.getUsername();
    }

    public void saveRole(Role role) {
        log.info("Saving role by name {} to the database", role.getRole());
        roleRepository.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRole(roleName);
        log.info("Saving role {} into user by name {} to the database", role.getRole(), user.getUsername());
        //        user.getRoles().add(role);
    }

    public User getUser(String username) {
        log.info("Searching user by name {} to the database", username);
        return userRepository.findByUsername(username);
    }

    public List<User> getUsers() {
        log.info("Searching All users to the database");
        return userRepository.findAll();
    }

    public Long removeUser(Long id) {
        log.info("Removing user by Id {} to the database", id);
        userRepository.deleteById(id);
        return id;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null){
            log.error("User {} not found", username);
            throw new NotFoundException("User not found");
        } else {
            log.info("User {} found", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRole())));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }
}