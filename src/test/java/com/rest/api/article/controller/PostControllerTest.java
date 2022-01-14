package com.rest.api.article.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PostController controller;


    @Test
    public void articlePostControllerTestJson() throws Exception {
        this.mockMvc.perform(
                        post("/api/v1/posts/")
                                .param("text", "test")
                                .param("content", "test"))
                .andDo(print())
                .andExpect(status().is(201));
    }
}