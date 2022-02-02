package com.rest.api.article.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "articles")
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "star")
    private boolean star = false;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "articles_tags",
            joinColumns = {
                    @JoinColumn(name = "article_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "tag_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Tag> hashtags = new HashSet<>();

//    @JoinColumn(name = "user_id")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private User user;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}