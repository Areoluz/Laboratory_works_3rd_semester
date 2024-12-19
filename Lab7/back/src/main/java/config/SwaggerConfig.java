package config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI mathApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Math Operations API")
                        .description("API for performing mathematical operations and logging")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Your Name")
                                .email("your.email@example.com")));
    }
}
