package com.softuni.Restaurant.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object object)  {
        System.out.println("1 - pre handle.");
        System.out.println("METHOD type:" + request.getMethod());
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Servlet PATH: " + request.getServletPath());
        //check which controller method is requested
        if(object instanceof HandlerMethod){
            Class<?> controllerClass = ((HandlerMethod) object).getBeanType();
            String methodName = ((HandlerMethod) object).getMethod().getName();
            System.out.println("Controller name: " + controllerClass.getName());
            System.out.println("Method name:" + methodName);
        }
        return true;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object object, ModelAndView model){
        System.out.println("2 - post handle.");
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object object, Exception exception){
        if(exception != null){
            //exception handle part
            System.out.println("An error occured.");
        }
        System.out.println("3 - after completion.");
    }
}
