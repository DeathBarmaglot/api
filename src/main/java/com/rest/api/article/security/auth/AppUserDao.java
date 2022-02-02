package com.rest.api.article.security.auth;

import java.util.Optional;

public interface AppUserDao {
    Optional<AppUser> selectAppUserByUsername(String username);
}
