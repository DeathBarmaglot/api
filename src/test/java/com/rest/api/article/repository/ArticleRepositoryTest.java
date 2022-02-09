//package com.rest.api.article.repository;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.rest.api.article.entity.Article;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.notNullValue;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class)
//class ArticleRepositoryTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    private final String HOST = "/api/v1/posts";
//
//    @InjectMocks
//    private final ArticleRepository articleRepository = Mockito.mock(ArticleRepository.class);
//
//    @Test
//    public void GetArticles() throws Exception {
//        String uri = HOST + "/";
//        Article article_mock = Article.builder().id(1L).title("Post").content("Test").build();
//        Article article_mock1 = Article.builder().id(2L).title("Article").content("New Test").build();
//        List<Article> listArticle = List.of(article_mock, article_mock1);
//
//        when(articleRepository.findAll()).thenReturn(listArticle);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get(uri)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$", hasSize(15)));
//
//    }
//
//    public String mapper(Object post) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
//                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
//        return objectWriter.writeValueAsString(post);
//    }
//}