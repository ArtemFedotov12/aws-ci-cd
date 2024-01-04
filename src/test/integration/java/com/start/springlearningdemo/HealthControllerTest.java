package com.start.springlearningdemo;

import com.start.springlearningdemo.controller.HealthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthController.class)
@Import(TestSecurityConfig.class)
public class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenCheckHealthThenSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/health"))
                .andExpect(status().isOk());
    }
}
