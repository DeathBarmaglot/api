package com.rest.api.blog.user;

import com.rest.api.blog.role.Role;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long user_id;

    private String username;

    private String email;

    private String password;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Collection<Role> roles;

    private Boolean locked = false;

    private Boolean enabled = false;

}