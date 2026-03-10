package com.campus.complaintmanagement.config;

// src/main/java/com/yourpackage/config/FilterConfig.java

import com.campus.complaintmanagement.filter.RateLimitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    private final RateLimitFilter rateLimitFilter;

    public FilterConfig(RateLimitFilter rateLimitFilter) {
        this.rateLimitFilter = rateLimitFilter;
    }

    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitRegistration() {
        FilterRegistrationBean<RateLimitFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(rateLimitFilter);
        bean.addUrlPatterns(
                "/student/complaints",  // complaint submission
                "/auth/login",          // login attempts
                "/student/register",    // registration
                "/admin/register"
        );
        bean.setOrder(1);
        return bean;
    }
}