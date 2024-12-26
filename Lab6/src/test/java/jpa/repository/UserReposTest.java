package jpa.repository;

import jpa.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import web.service.application.Application;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class)
public class UserReposTest {

    @Autowired
    private UserRepos userRepos;

    private User user;

    private String uniqueSuffix;

    @BeforeEach
    void setUp() {
        uniqueSuffix = UUID.randomUUID().toString();

        user = new User();
        user.setUsername("testuser_" + uniqueSuffix);
        user.setPassword("password123");

        userRepos.save(user);
    }

    @Test
    void shouldFindUserByUsername() {
        User foundUser = userRepos.findByUsername("testuser_" + uniqueSuffix);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testuser_" + uniqueSuffix);
    }


    @Test
    void shouldFindUserByUsernameAndPassword() {
        User foundUser = userRepos.findByUsernameAndPassword("testuser_" + uniqueSuffix, "password123");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testuser_" + uniqueSuffix);
        assertThat(foundUser.getPassword()).isEqualTo("password123");
    }

    @Test
    void shouldReturnNullWhenUserNotFoundByUsername() {
        User foundUser = userRepos.findByUsername("nonexistent");
        assertThat(foundUser).isNull();
    }
}
