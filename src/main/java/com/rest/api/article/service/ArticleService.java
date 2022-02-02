package com.rest.api.article.service;

import com.rest.api.article.dto.CommentWithoutPostDto;
import com.rest.api.article.dto.PostWithCommentsDto;
import com.rest.api.article.dto.PostWithoutCommentDto;
import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import com.rest.api.article.service.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService extends DtoMapper {

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
        List<PostWithoutCommentDto> postList = new ArrayList<>();

        if (title.isPresent()) {
            result = articleRepository.findByTitle(title.get());
            log.info("Founded article by title {} to the database", title.get());

        } else if (sort.isPresent()) {
            result = articleRepository.findAll(PageRequest.of(
                    page.orElse(0), 100, Sort.Direction.ASC,
                    sort.orElse("id"))).getContent();
            log.info("Sorted article by {} to the database", sort.get());

        } else {
            result = articleRepository.findAll();
            log.info("Searching all articles in the database");
        }

        result.forEach(post -> postList.add(dtoPost(post)));

        return postList;
    }

    public List<PostWithCommentsDto> getFullArticle() {
        List<Article> articleDb = articleRepository.findAll();
        List<PostWithCommentsDto> all = new ArrayList<>();

        articleDb.forEach(article ->
                all.add(getPostWithComment(article)));

        return all;
    }

    public PostWithCommentsDto getPostWithComment(Article article) {
        PostWithCommentsDto post = new PostWithCommentsDto();
        BeanUtils.copyProperties(dtoPost(article), post, "comment");
        post.setComments(fetch(article));
        return post;
    }

    private PostWithoutCommentDto dtoPost(Article article) {
        PostWithoutCommentDto postDto = new PostWithoutCommentDto();
        BeanUtils.copyProperties(article, postDto, "comment", "hashtags");
        postDto.setTags(tagsMapper(article).get("tags"));
        return postDto;
    }

    private List<CommentWithoutPostDto> fetch(Article article) {
        List<CommentWithoutPostDto> commentWithoutPostDtoList = new ArrayList<>();
        List<Comment> comments = commentRepository.findByArticle(article, Sort.unsorted());

        comments.forEach(comment -> commentWithoutPostDtoList.add(dtoComment(comment)));

        return commentWithoutPostDtoList;
    }

    private CommentWithoutPostDto dtoComment(Comment comment) {
        CommentWithoutPostDto commentWithoutPostDto = new CommentWithoutPostDto();
        BeanUtils.copyProperties(comment, commentWithoutPostDto, "article");
        return commentWithoutPostDto;
    }
}
