package web.service.controller;

import jpa.entities.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import web.service.service.LogService;
import web.service.application.Application;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogService logService; // Mock the LogService

    private Log log1;
    private Log log2;
    private List<Log> logList;

    @BeforeEach
    void setUp() {
        log1 = new Log();
        log1.setId(1);
        log1.setMessage("Test message 1");
        log1.setTimestamp(Timestamp.from(Instant.now()));

        log2 = new Log();
        log2.setId(2);
        log2.setMessage("Test message 2");
        log2.setTimestamp(Timestamp.from(Instant.now().plusSeconds(60)));

        logList = Arrays.asList(log1, log2);
    }

    @Test
    void getAllShouldReturnListOfLogs() throws Exception {
        when(logService.findAll()).thenReturn(logList);

        mockMvc.perform(get("/api/logs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].message").value("Test message 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].message").value("Test message 2"));

        verify(logService).findAll();
    }

    @Test
    void getAllSortedShouldReturnSortedLogs() throws Exception {
        when(logService.findByOrderByTimestampDesc()).thenReturn(logList);

        mockMvc.perform(get("/api/logs/sorted"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(logService).findByOrderByTimestampDesc();
    }

    @Test
    void deleteAllLogsShouldReturnOk() throws Exception {
        doNothing().when(logService).deleteAll();

        mockMvc.perform(delete("/api/logs"))
                .andExpect(status().isOk());

        verify(logService).deleteAll();
    }

    @Test
    void deleteLogShouldReturnOk() throws Exception {
        doNothing().when(logService).deleteById(anyInt());

        mockMvc.perform(delete("/api/logs/1"))
                .andExpect(status().isOk());

        verify(logService).deleteById(1);
    }

    @Test
    void getAllShouldHandleError() throws Exception {
        when(logService.findAll()).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/api/logs"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getAllSortedShouldHandleError() throws Exception {
        when(logService.findByOrderByTimestampDesc()).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/api/logs/sorted"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void addLogShouldReturnCreated() throws Exception {
        Log newLog = new Log();
        newLog.setId(3);
        newLog.setMessage("New log message");
        newLog.setTimestamp(Timestamp.from(Instant.now()));

        // Преобразуем Timestamp в строку с нужным форматом
        String timestampString = newLog.getTimestamp().toInstant().toString(); // Преобразуем в ISO 8601

        when(logService.addLog(any(Log.class))).thenReturn(newLog);

        mockMvc.perform(post("/api/logs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"New log message\",\"timestamp\":\"" + timestampString + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.message").value("New log message"));

        verify(logService).addLog(any(Log.class));
    }

    @Test
    void updateLogShouldReturnUpdated() throws Exception {
        Log updatedLog = new Log();
        updatedLog.setId(1);
        updatedLog.setMessage("Updated log message");
        updatedLog.setTimestamp(Timestamp.from(Instant.now()));

        // Преобразуем Timestamp в строку с нужным форматом
        String timestampString = updatedLog.getTimestamp().toInstant().toString(); // Преобразуем в ISO 8601

        when(logService.updateLog(anyInt(), any(Log.class))).thenReturn(updatedLog);

        mockMvc.perform(put("/api/logs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"Updated log message\",\"timestamp\":\"" + timestampString + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.message").value("Updated log message"));

        verify(logService).updateLog(eq(1), any(Log.class));
    }
}
