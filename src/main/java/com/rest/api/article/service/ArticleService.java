package com.rest.api.article.service;

import com.rest.api.article.dto.CommentWithoutPostDto;
import com.rest.api.article.dto.PostWithCommentsDto;
import com.rest.api.article.dto.PostWithoutCommentDto;
import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public List<Article> getByStar() {
        log.info("Searching top articles");
        return articleRepository.findByStarTrue();
    }

    public List<Article> getAll() {
        log.info("Searching All articles");
        return articleRepository.findAll();
    }

    public void removeArticle(Article article) {
        log.info("Removing post {} to the database", article.getTitle());
        articleRepository.deleteAllById(Collections.singleton(article.getId()));
    }

    public Article toggle(Article articleDb, boolean star) {
        log.info("Setting top article {} as {}", articleDb.getTitle(), star);
        articleDb.setStar(star);
        return articleRepository.save(articleDb);
    }

    public Article addNewArticle(Article article) {
        log.info("Saving article {} to the database", article.getTitle());
        return articleRepository.save(article);
    }

    public Article getArticle(Long id) {
        log.info("Searching article by Id {} to the database", id);
        return articleRepository.findById(id).orElseThrow();
    }

    public Article updateArticle(Article articleDb, Article article) {
        log.info("Updating article {} to the database", article.getTitle());
        BeanUtils.copyProperties(article, articleDb, "id");
        return articleRepository.save(articleDb);
    }

    public List<PostWithoutCommentDto> filteredBy(
            Optional<String> sort,
            Optional<String> title,
            Optional<Integer> page) {

        List<Article> result;

        if (title.isPresent()) {
            result = articleRepository.findByTitle(title.get());
            log.info("Founded article by title {} to the database", title.get());

        } else if (sort.isPresent()) {
            result = articleRepository.findAll(PageRequest.of(
                    page.orElse(0), 100, Sort.Direction.ASC, sort.orElse("id"))).getContent();
            log.info("Sorted article by {} to the database", sort.get());

        } else {
            result = articleRepository.findAll();
            log.info("Searching all articles in the database");
        }

        List<PostWithoutCommentDto> postWithoutCommentDtoList = new ArrayList<>();

        result.forEach(article ->
                postWithoutCommentDtoList.add(dtoArticleMapper(article)));

        return postWithoutCommentDtoList;
    }

    private PostWithoutCommentDto dtoArticleMapper(Article article) {
        PostWithoutCommentDto postWithoutCommentDto = new PostWithoutCommentDto();
        BeanUtils.copyProperties(article, postWithoutCommentDto, "comment");
        return postWithoutCommentDto;
    }

    public List<PostWithCommentsDto> getFullArticle() {
        List<Article> articleDb = articleRepository.findAll();
        List<PostWithCommentsDto> all = new ArrayList<>();

        articleDb.forEach(article ->
                all.add(putPost(article)));
        return all;
    }

    protected PostWithCommentsDto putPost(Article article) {
        PostWithCommentsDto post = new PostWithCommentsDto();
        BeanUtils.copyProperties(article, post, "comment");
        post.setComments(fetch(article));
        return post;
    }

    private List<CommentWithoutPostDto> fetch(Article article) {
        List<CommentWithoutPostDto> commentWithoutPostDtoList = new ArrayList<>();
        List<Comment> comments = commentRepository.findByArticle(article, Sort.unsorted());

        comments.forEach(comment -> commentWithoutPostDtoList.add(dtoCommentMapper(comment)));

        return commentWithoutPostDtoList;
    }

    private CommentWithoutPostDto dtoCommentMapper(Comment comment) {
        CommentWithoutPostDto commentWithoutPostDto = new CommentWithoutPostDto();
        BeanUtils.copyProperties(comment, commentWithoutPostDto, "article");
        return commentWithoutPostDto;
    }
}
