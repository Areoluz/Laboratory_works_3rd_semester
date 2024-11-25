package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "jpa.entities")
@EnableJpaRepositories(basePackages = "jpa.repository")
public class MathApplication {
    public static void main(String[] args) {
        SpringApplication.run(MathApplication.class, args);
    }
}
