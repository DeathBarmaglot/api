package com.rest.api;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void articleGetControllerTestStatusOk() throws Exception {
        this.mockMvc.perform(get("/api/v1/posts"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}