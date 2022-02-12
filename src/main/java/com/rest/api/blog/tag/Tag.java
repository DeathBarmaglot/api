package com.rest.api.blog.tag;

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
@Table(name = "tags")
public class Tag implements Serializable {
    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String tag;

    @ManyToMany(mappedBy = "tags")
    private Set<Article> articles;
}
