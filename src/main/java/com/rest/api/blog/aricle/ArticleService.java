package com.rest.api.blog.aricle;

import com.rest.api.blog.dto.PostWithCommentsDto;
import com.rest.api.blog.dto.PostWithoutCommentDto;
import com.rest.api.blog.comment.Comment;
import com.rest.api.blog.tag.Tag;
import com.rest.api.blog.comment.CommentRepository;
import com.rest.api.blog.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    public List<PostWithoutCommentDto> getAll() {
        log.info("Searching All articles withOut Comments");
        List<Article> articles = articleRepository.findAll();
        return getPostsWithOutComments(articles);

    }

    public Long removeArticle(Long id) {
        articleRepository.deleteById(id);
        log.info("Removing post {} to the database", id);
        return id;
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

    public PostWithCommentsDto getAllCommentsByPostId(Long id) {
        log.info("Fetching all comments {} to the database", id);
        Article article = articleRepository.getById(id);
        PostWithCommentsDto full = new PostWithCommentsDto();
        BeanUtils.copyProperties(article, full, "tags");
        full.setComments(fetchCommentsDto(commentRepository.findByArticleId(id)));
//        full.setTags(getTagsDto(article.getTags()));
        return full;
    }

    private Set<String> getTagsDto(Set<Tag> tags) {
        Set<String> setTags = new HashSet<>();
        tags.forEach(tag -> setTags.add(tag.getTag()));
        return setTags;
    }

    private List<PostWithoutCommentDto> getPostsWithOutComments(List<Article> articles) {
        List<PostWithoutCommentDto> postList = new ArrayList<>();
        articles.forEach(article -> postList.add(getPostWithOutComment(article)));
        return postList;
    }

    private PostWithoutCommentDto getPostWithOutComment(Article article) {
        PostWithoutCommentDto postDto = new PostWithoutCommentDto();
        BeanUtils.copyProperties(article, postDto, "comment", "tags");
        Set<String> tags = new HashSet<>();
        article.getTags().forEach(tag -> tags.add(tag.getTag()));
        postDto.setTags(tags);
        return postDto;
    }

    public List<List<Comment>> getFullArticles() {
        log.info("Searching All articles with all comments");
        List<Article> articles = articleRepository.findAll();
        List<Long> ids = new ArrayList<>();
        Map<Long, Article> map = new HashMap<>();

        articles.forEach(article -> {
            ids.add(article.getId());
        });
        List<List<Comment>> commentsByArticle = commentRepository.findCommentsByArticle(ids);
        return commentsByArticle;
    }
}