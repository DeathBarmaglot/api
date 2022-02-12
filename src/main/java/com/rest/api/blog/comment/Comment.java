package com.rest.api.blog.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rest.api.blog.aricle.Article;
import com.rest.api.blog.user.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment implements Serializable {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne()
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}