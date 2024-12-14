package web.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jpa.entities.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import web.service.service.LogService;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@Tag(name = "Log Controller", description = "Endpoints for managing application logs")
public class LogController {
    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @Operation(summary = "Get all logs", description = "Retrieves all logs from the database")
    @GetMapping
    public ResponseEntity<List<Log>> getAll() {
        try {
            return ResponseEntity.ok(logService.findAll());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving logs", e);
        }
    }

    @Operation(summary = "Get sorted logs", description = "Retrieves all logs sorted by timestamp in descending order")
    @GetMapping("/sorted")
    public ResponseEntity<List<Log>> getAllSorted() {
        try {
            return ResponseEntity.ok(logService.findByOrderByTimestampDesc());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving sorted logs", e);
        }
    }

    @Operation(summary = "Delete log by ID", description = "Deletes a specific log entry by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable int id) {
        try {
            logService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting log", e);
        }
    }

    @Operation(summary = "Delete all logs", description = "Deletes all logs from the database")
    @DeleteMapping
    public ResponseEntity<Void> deleteAllLogs() {
        try {
            logService.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting all logs", e);
        }
    }
}
