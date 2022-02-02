package com.rest.api.article.dao;

import com.rest.api.article.entity.Role;
import com.rest.api.article.entity.User;

import java.util.List;

public interface UserDao {
    User saveUser(User user);

    Role saveRole(Role role);

    void allRoleToUser(String username, String roleName);

    User getUser(String username);

    List<User> getUsers();

    void removeUser(User user);
}
