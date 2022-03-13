package com.test.propertyCommuity.config;

import com.test.propertyCommuity.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor()).excludePathPatterns("/auth/**");
    }

    @Bean AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }
}

