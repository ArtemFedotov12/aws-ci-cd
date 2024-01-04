package com.start.springlearningdemo;

import com.start.springlearningdemo.controller.HealthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@WebMvcTest(HealthController.class)
//@ActiveProfiles("test")
//@Import(TestSecurityConfig.class)
public class SpringLearningDemoApplicationIT extends BaseServiceIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() {
	}

	@Test
	//@WithMockUser(roles = "ADMIN")
	//@WithAnonymousUser
	void whenCheckHealthThenSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/health")
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
