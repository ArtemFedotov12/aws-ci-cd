package com.start.springlearningdemo.config;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// config for annotations like "@CreatedDate"
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || isBlank(authentication.getName())) {
      return Optional.of("SYSTEM");
    }
    return Optional.of(authentication.getName());
  }
}
