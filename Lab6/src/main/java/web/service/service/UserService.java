package web.service.service;

import jpa.entities.User;
import jpa.repository.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsManager {
    @Autowired
    UserRepos userRepos;

    @Override
    public void createUser(UserDetails user) {
    userRepos.save((User) user);
    }

    @Override
    public void updateUser(UserDetails user) {
    userRepos.save((User) user);
    }

    @Override
    public void deleteUser(String username) {
    userRepos.deleteByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
throw new UnsupportedOperationException("Not supported and wont be.");
    }

    @Override
    public boolean userExists(String username) {
        return userRepos.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepos.findByUsername(username);
    }
}
