package com.rest.api.article.service;


import com.rest.api.article.entity.User;
import com.rest.api.article.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User addNewUser(User user) {
        userRepository.save(user);
        return user;
    }
}
