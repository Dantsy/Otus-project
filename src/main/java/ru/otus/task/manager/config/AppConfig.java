package ru.otus.task.manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.otus.task.manager.middleware.LoggingInterceptor;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Primary
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor())
                .addPathPatterns("/tasks/**")
                .excludePathPatterns("/tasks/{taskId}/**");
    }
}