package web.service.service;

import jpa.entities.Log;
import jpa.repository.LogRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    // Добавить новый лог
    @Transactional
    public Log addLog(Log log) {
        return logRepository.save(log);
    }

    // Обновить существующий лог
    @Transactional
    public Log updateLog(int id, Log updatedLog) {
        Optional<Log> existingLog = logRepository.findById(id);
        if (existingLog.isPresent()) {
            Log log = existingLog.get();
            // Обновляем только те поля, которые можно изменить
            log.setMessage(updatedLog.getMessage());
            log.setTimestamp(updatedLog.getTimestamp());
            return logRepository.save(log);
        } else {
            throw new RuntimeException("Log with id " + id + " not found");
        }
    }
}