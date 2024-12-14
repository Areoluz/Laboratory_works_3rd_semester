package web.service.service;

import jpa.entities.Log;
import jpa.repository.LogRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LogService {
    private final LogRepos logRepository;

    @Autowired
    public LogService(LogRepos logRepository) {
        this.logRepository = logRepository;
    }

    public List<Log> findAll() {
        return (List<Log>) logRepository.findAll();
    }

    public List<Log> findByOrderByTimestampDesc() {
        return logRepository.findByOrderByTimestampDesc();
    }

    @Transactional
    public void deleteById(int id) {
        logRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        logRepository.deleteAll();
    }
}