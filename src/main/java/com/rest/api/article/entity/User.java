package com.rest.api.article.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    @Column(name="user_id")
    private Long id;

    private String username;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role roles;

    private Boolean locked = false;

    private Boolean enabled = false;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<Article> articles;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Comment> comments;
}