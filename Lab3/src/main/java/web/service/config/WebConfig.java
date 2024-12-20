package web.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000") // Укажите продакшн-домен в будущем
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // Полный список методов
                .allowedHeaders("*") // Разрешить любые заголовки
                .allowCredentials(true); // Для передачи cookie или авторизационных данных
    }
}
