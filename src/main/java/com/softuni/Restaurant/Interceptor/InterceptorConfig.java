package com.softuni.Restaurant.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoggingInterceptor loggingInterceptor;

    @Autowired
    private RequestInterceptor requestInterceptor;

    @Autowired
    private LoggingInterceptorIp loggingInterceptorIp;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
        registry.addInterceptor(requestInterceptor);
        registry.addInterceptor(loggingInterceptorIp)
                .addPathPatterns("/**");  // Apply it to all URLs
    }
}
