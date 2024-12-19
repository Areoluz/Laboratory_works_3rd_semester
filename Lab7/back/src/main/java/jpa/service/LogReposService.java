package jpa.service;

import jpa.entities.Log;
import jpa.repository.LogRepos;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class LogReposService {
    @Delegate(types = LogRepos.class)

    private LogRepos logRepos;

}
