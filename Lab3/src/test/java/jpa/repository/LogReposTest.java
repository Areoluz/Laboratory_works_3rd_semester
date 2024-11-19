package jpa.repository;

import jpa.DbConfig;
import jpa.entities.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {LogRepos.class, DbConfig.class})
@ExtendWith(SpringExtension.class)
class LogReposTest {

    @Autowired
    private LogRepos logRepos;

    @Test
    void testCreateAndRead() {
        logRepos.deleteAll();
        assertEquals(0, logRepos.count());

        // Создание нового лога
        Log log = new Log();
        log.setMessage("Test log message");
        log.setTimestamp(Timestamp.from(Instant.now()));

        Log savedLog = logRepos.save(log);
        assertNotNull(savedLog.getId());
        assertEquals(1, logRepos.count());

        Optional<Log> fetchedLog = logRepos.findById(savedLog.getId());
        assertTrue(fetchedLog.isPresent());
        assertEquals(log.getMessage(), fetchedLog.get().getMessage());
    }

    @Test
    void testUpdate() {
        logRepos.deleteAll();
        Log log = new Log();
        log.setMessage("Test log message");
        log.setTimestamp(Timestamp.from(Instant.now()));

        Log savedLog = logRepos.save(log);
        assertNotNull(savedLog.getId());

        savedLog.setMessage("Updated message");
        logRepos.save(savedLog);

        Optional<Log> updatedLog = logRepos.findById(savedLog.getId());
        assertTrue(updatedLog.isPresent(), "Updated log should be present");
        assertEquals("Updated message", updatedLog.get().getMessage());
    }

    @Test
    void testDelete() {
        logRepos.deleteAll();
        Log log = new Log();
        log.setMessage("Message to delete");
        log.setTimestamp(Timestamp.from(Instant.now()));

        Log savedLog = logRepos.save(log);
        assertNotNull(savedLog.getId());
        assertEquals(1, logRepos.count());

        logRepos.delete(savedLog);
        assertEquals(0, logRepos.count());
    }
}
