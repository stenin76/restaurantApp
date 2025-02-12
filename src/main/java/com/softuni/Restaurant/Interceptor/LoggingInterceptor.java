package com.softuni.Restaurant.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        logger.info("Request received for URL: " + request.getRequestURL().toString());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) {
        logger.info("Request processing completed for URL: " + request.getRequestURL().toString());
    }
}
