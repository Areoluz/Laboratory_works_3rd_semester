package jpa.repository;

import jpa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepos extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByUsernameAndPassword(String username, String password);
    User findByEmailAndPassword(String email, String password);
    Optional<User> findByOauth2ProviderAndOauth2ProviderId(String oauth2Provider, String oauth2ProviderId);
}