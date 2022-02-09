package com.rest.api.article.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
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
//    @JoinColumn(name = "article_id")
//    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Article article;

//    @ManyToOne()
//    @JoinColumn(name = "user_id")
//    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
//    private User user;
}