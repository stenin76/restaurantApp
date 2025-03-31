package com.softuni.Restaurant.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoggingInterceptorIp implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Get the IP address of the client making the request
        String ipAddress = request.getRemoteAddr();

        // Log the IP address (you can use any logging framework like SLF4J, Log4j, etc.)
        System.out.println("Client IP Address: " + ipAddress);

        // You can add this information to the request attributes if you want to use it later
        request.setAttribute("clientIp", ipAddress);

        return true; // Allow the request to proceed to the controller
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Post-processing logic (can be used if you want to manipulate the response or model)
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Cleanup logic after the request has been processed (e.g., logging or exception handling)
    }
}
