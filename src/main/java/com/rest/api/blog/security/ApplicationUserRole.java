//package com.rest.api.blog.security;
//
//import com.google.common.collect.Sets;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import static com.rest.api.blog.security.ApplicationUserPermission.*;
//
//public enum ApplicationUserRole {
//    USER(Sets.newHashSet(ROLE_READER, ROLE_WRITER)),
//    ADMIN(Sets.newHashSet(ROLE_ADMIN, ROLE_USER, ROLE_READER, ROLE_WRITER)),
//    MANAGER(Sets.newHashSet(ROLE_USER, ROLE_READER, ROLE_WRITER));
//
//    private final Set<ApplicationUserPermission> permissions;
//
//    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
//        this.permissions = permissions;
//    }
//
//    public Set<ApplicationUserPermission> getPermissions() {
//        return permissions;
//    }
//
//    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
//        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//                .collect(Collectors.toSet());
//        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//        return permissions;
//    }
//}