package com.rest.api.article.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "articles")
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "star")
    private boolean star = false;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<Comment> comments = new HashSet<>();

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}