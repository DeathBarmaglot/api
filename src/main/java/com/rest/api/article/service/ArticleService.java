package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    public Optional<Article> getArticle(Long id) {
        return articleRepository.findById(id);
    }

    public void removeArticle(Long id) {
        Article remove = articleRepository.getById(id);
        List<Comment> list = commentRepository.findByArticle(remove, Sort.unsorted());
        list.forEach(comment -> commentRepository.deleteById(comment.getId()));
        articleRepository.delete(remove);
    }

    public Article toggle(Long id, boolean isStar) {

        Article newArticle = articleRepository.getById(id);

        return articleRepository.findById(id)
                .map(article -> {
                    article.setStar(isStar);
                    return articleRepository.save(article);
                })
                .orElseGet(() -> {
                    newArticle.setStar(isStar);
                    return articleRepository.save(newArticle);
                });
    }

    public Article updateArticle(Article newArticle, Long id) {

        return articleRepository.findById(id)
                .map(article -> {
                    article.setTitle(newArticle.getTitle());
                    article.setContent(newArticle.getContent());
                    article.setStar(newArticle.isStar());
                    return articleRepository.save(article);
                })
                .orElseGet(() -> {
                    newArticle.setId(id);
                    return articleRepository.save(newArticle);
                });
    }

    public ResponseEntity<Optional<Article>> addNewArticle(Article article) {
        Article newArticle = new Article(article.getTitle(), article.getContent());
        saveArticle(newArticle);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/posts").toUriString());
        return ResponseEntity.created(uri).body(getArticle(newArticle.getId()));
    }
}
