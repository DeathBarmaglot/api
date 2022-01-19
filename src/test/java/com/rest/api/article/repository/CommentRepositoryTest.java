package com.rest.api.article.repository;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CommentRepositoryTest {

    @Autowired
    private MockMvc mockMvc;
    private final String HOST = "/api/v1/posts";

    @InjectMocks
    private final CommentRepository commentRepository = Mockito.mock(CommentRepository.class);

    Article ARTICLE = new Article(1L, "Test", "add", true);
    Comment COMMENT = new Comment(2L, "Comment", null, ARTICLE);
    Comment COMMENT1 = new Comment(3L, "Comment2", null, ARTICLE);


    @Test
    public void TestFindByArticle() throws Exception {
        List<Comment> listComment = commentRepository.findByArticle(ARTICLE, Sort.unsorted());
        System.out.println(listComment);

        String uri = HOST + "/1/full";
        List<Comment> commentList = new ArrayList<>(Arrays.asList(COMMENT, COMMENT1));

        Mockito.when(commentRepository.findAll()).thenReturn(commentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].Comment", is("Comment")));
    }
}