package com.rest.api.article.service;

import com.rest.api.article.dto.CommentWithPostDto;
import com.rest.api.article.dto.CommentWithoutPostDto;
import com.rest.api.article.dto.PostWithCommentsDto;
import com.rest.api.article.dto.PostWithoutCommentDto;
import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.entity.Tag;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import com.rest.api.article.service.utils.DtoMapper;
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


//    public List<Article> filteredBy(
//            Optional<String> sort,
//            Optional<String> title,
//            Optional<Integer> page) {


//        if (title.isPresent()) {
//            result = articleRepository.findByTitle(title.get());
//            log.info("Founded article by title {} to the database", title.get());
//
//        } else if (sort.isPresent()) {
//            result = articleRepository.findAll(PageRequest.of(
//                    page.orElse(0), 100, Sort.Direction.ASC,
//                    sort.orElse("id"))).getContent();
//            log.info("Sorted article by {} to the database", sort.get());
//
//        } else {
//            result = articleRepository.findAll();
//            log.info("Searching all articles in the database");
//        }

//        result.forEach(post -> postList.add(dtoPost(post)));

//        return postList;
//

//      private PostWithoutCommentDto dtoPost(Article article) {
//        PostWithoutCommentDto postDto = new PostWithoutCommentDto();
//        BeanUtils.copyProperties(article, postDto, "comment", "tags");
//        postDto.setTags(tagsMapper(article).get("tags"));
//        return postDto;
//    }


    private List<CommentWithoutPostDto> fetchCommentsDto(List<Comment> comments) {
        List<CommentWithoutPostDto> commentWithoutPostDtoList = new ArrayList<>();
        comments.forEach(comment -> commentWithoutPostDtoList.add(dtoComment(comment)));
        return commentWithoutPostDtoList;
    }

    private CommentWithoutPostDto dtoComment(Comment comment) {
        CommentWithoutPostDto commentWithoutPostDto = new CommentWithoutPostDto();
        BeanUtils.copyProperties(comment, commentWithoutPostDto, "article");
        return commentWithoutPostDto;
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
//        commentsByArticle.forEach(comments ->
//                comments.forEach(comment -> map.put(comment.getArticle().getId(),
//                getPostsWithComments(comment.getArticle(), comments))));

        return commentsByArticle;
    }

    private PostWithCommentsDto getPostsWithComments(Article article, List<Comment> comments) {
        PostWithCommentsDto post = new PostWithCommentsDto();
        BeanUtils.copyProperties(article, post, "comment");
        post.setComments(fetchCommentsDto(comments));
        return post;
    }
}