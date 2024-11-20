package jpa.service;

import jpa.entities.Log;
import jpa.repository.LogRepos;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class LogReposService {

    private LogRepos logRepos;

    public List<Log> findByOrderByTimestampDesc() {
        return logRepos.findByOrderByTimestampDesc();
    }

}
