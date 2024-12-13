package jpa.repository;

import jpa.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import web.service.application.Application;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class)
public class UserReposTest {

    @Autowired
    private UserRepos userRepos;

    private User user;

    // Generate a unique suffix for each test
    private String uniqueSuffix;

    @BeforeEach
    void setUp() {
        uniqueSuffix = UUID.randomUUID().toString();  // Generate a unique suffix for each test

        // Create a new user with a unique email and username for each test
        user = new User();
        user.setUsername("testuser_" + uniqueSuffix);
        user.setPassword("password123");
        user.setEmail("testuser_" + uniqueSuffix + "@example.com");
        user.setOauth2Provider("google");
        user.setOauth2ProviderId("12345");

        // Save the user to the repository
        userRepos.save(user);
    }

    @Test
    void shouldFindUserByUsername() {
        User foundUser = userRepos.findByUsername("testuser_" + uniqueSuffix);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testuser_" + uniqueSuffix);
    }

    @Test
    void shouldFindUserByEmail() {
        User foundUser = userRepos.findByEmail("testuser_" + uniqueSuffix + "@example.com");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("testuser_" + uniqueSuffix + "@example.com");
    }

    @Test
    void shouldFindUserByUsernameAndPassword() {
        User foundUser = userRepos.findByUsernameAndPassword("testuser_" + uniqueSuffix, "password123");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testuser_" + uniqueSuffix);
        assertThat(foundUser.getPassword()).isEqualTo("password123");
    }

    @Test
    void shouldFindUserByEmailAndPassword() {
        User foundUser = userRepos.findByEmailAndPassword("testuser_" + uniqueSuffix + "@example.com", "password123");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("testuser_" + uniqueSuffix + "@example.com");
        assertThat(foundUser.getPassword()).isEqualTo("password123");
    }

    @Test
    void shouldReturnNullWhenUserNotFoundByUsername() {
        User foundUser = userRepos.findByUsername("nonexistent");
        assertThat(foundUser).isNull();
    }
}
