package com.rest.api.blog.aricle;

import com.rest.api.blog.tag.Tag;
import com.rest.api.blog.user.User;
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
@Table(name = "articles")
public class Article implements Serializable {
    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column(columnDefinition = "boolean default false")
    private Boolean star = false;

    @ManyToMany
    @JoinTable(name = "article_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private Set<Tag> tags;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}