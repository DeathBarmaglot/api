//package com.rest.api.article.service;
//
//import com.rest.api.article.repository.TagRepository;
//import org.junit.jupiter.api.Test;
//
//import com.rest.api.article.repository.ArticleRepository;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith(MockitoExtension.class)
//class TagTagServiceTest {
//
//
//    @Mock
//    private ArticleRepository articleRepository;
//    private TagRepository tagRepository;
//
//        @Test
//    void getAll() {
//            TagService tagService = new TagService(tagRepository, articleRepository);
//            Tag tag_mock = Tag.builder().id(1L).hashtag("test").build();
//            Tag tag_mock1 = Tag.builder().id(2L).hashtag("tag").build();
//            Set<Tag> tagSet = new HashSet<>();
//            tagSet.add(tag_mock);
//            tagSet.add(tag_mock1);
//            Article article_mock1 = Article.builder().id(2L).title("Article").content("New Test").tags(tagSet).build();
//
//             Article article_mock = Article.builder().id(1L).title("Post").content("Test").tags(tagSet).build();
//            List<Article> listArticle = List.of(article_mock, article_mock1);
//
//            when(tagRepository.findAll()).thenReturn((List<HashTag>) tagSet);
//            Set<HashTag> actual = tagService.getAll(article_mock1);
//            assertEquals(tagSet.size(), actual.size());
//            verify(articleRepository, times(1)).findAll();
//        }
//
//    @Test
//    void addNewTag() {
//    }
//
//    @Test
//    void removeTag() {
//    }
//
//    @Test
//    void getArticlesByTags() {
//    }
//}