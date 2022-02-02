package com.rest.api.article.security.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.rest.api.article.security.ApplicationUserRole.ADMIN;
import static com.rest.api.article.security.ApplicationUserRole.USER;

@Repository("fake")
public class FakeAppUserDaoService implements AppUserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeAppUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<AppUser> selectAppUserByUsername(String username) {
        return getAppUsers().stream().filter(user -> username.equals(user.getUsername())).findFirst();
    }

    private List<AppUser> getAppUsers() {
        List<AppUser> appUsers = Lists.newArrayList(
                new AppUser(
                        "jack",
                        passwordEncoder.encode("1"),
                        USER.getGrantedAuthorities(),
                        true, true, true, true
                ),
                new AppUser(
                        "joe",
                        passwordEncoder.encode("1"),
                        ADMIN.getGrantedAuthorities(),
                        true, true, true, true
                )
        );
        return appUsers;
    }
}
