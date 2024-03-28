package ru.otus.task.manager.middleware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        logger.info("Received request: {} {}", request.getMethod(), request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("Request processed: {} {} - Status: {}", request.getMethod(), request.getRequestURI(), response.getStatus());
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) throws Exception {
        if (ex != null) {
            logger.error("Exception occurred during request processing: {} {}", request.getMethod(), request.getRequestURI(), ex);
        } else {
            logger.info("Request completed: {} {} - Status: {}", request.getMethod(), request.getRequestURI(), response.getStatus());
        }
    }
}