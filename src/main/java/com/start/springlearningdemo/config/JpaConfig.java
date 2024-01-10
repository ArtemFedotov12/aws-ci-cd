package com.start.springlearningdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
// config for annotations like "@CreatedDate"
public class JpaConfig {

  @Bean
  public AuditorAware<String> auditorAware() {
    return new AuditorAwareImpl();
  }
}
