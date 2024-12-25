package ui.services;

import jpa.repository.UserRepos;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StorageService extends AbstractSecurityService {

    private UserRepos userRepos;



}
