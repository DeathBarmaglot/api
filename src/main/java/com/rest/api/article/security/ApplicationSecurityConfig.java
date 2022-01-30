package com.rest.api.article.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.rest.api.article.security.ApplicationUserPermission.ADMIN_WRITE;
import static com.rest.api.article.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll()
                .antMatchers("/api/**").hasRole(USER.name())
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(ADMIN_WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/api/**").hasAuthority(ADMIN_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/api/**").hasAuthority(ADMIN_WRITE.getPermission())
                .antMatchers("/api/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails johnDoe = User.builder()
                .username("john")
                .password(passwordEncoder.encode("u"))
                .authorities(USER.getGrantedAuthorities())
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("a"))
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails janeDoe = User.builder()
                .username("jane")
                .password(passwordEncoder.encode("m"))
                .authorities(MANAGER.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                admin, janeDoe, johnDoe
        );
    }
}