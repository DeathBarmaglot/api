package com.rest.api.blog.role;

import com.rest.api.blog.aricle.Article;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role implements Serializable {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String role;

    @ManyToMany()
    private Set<Article> articles;
}