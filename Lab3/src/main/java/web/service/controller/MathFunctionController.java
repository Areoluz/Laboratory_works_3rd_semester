package web.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jpa.entities.MathRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import web.service.service.MathService;

import java.util.List;

@RestController
@RequestMapping("/api/math")
@Tag(name = "Math Function Controller", description = "Endpoints for mathematical function operations")
public class MathFunctionController {
    private final MathService mathService;

    @Autowired
    public MathFunctionController(MathService mathService) {
        this.mathService = mathService;
    }

    @Operation(summary = "Get all math results", description = "Retrieves all mathematical function results from the database")
    @GetMapping
    public ResponseEntity<List<MathRes>> getAll() {
        try {
            return ResponseEntity.ok((List<MathRes>) mathService.findAll());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving math results", e);
        }
    }

    @Operation(summary = "Get results by hash", description = "Retrieves mathematical function results for a specific hash value")
    @GetMapping("/{hash}")
    public ResponseEntity<List<MathRes>> getByHash(@PathVariable long hash) {
        try {
            return ResponseEntity.ok(mathService.findByHash(hash));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving math results by hash", e);
        }
    }

    @Operation(summary = "Delete all results", description = "Deletes all mathematical function results from the database")
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        try {
            mathService.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting all math results", e);
        }
    }

    @Operation(summary = "Delete results by hash", description = "Deletes all mathematical function results for a specific hash value")
    @DeleteMapping("/{hash}")
    public ResponseEntity<Void> deleteByHash(@PathVariable long hash) {
        try {
            mathService.deleteByHash(hash);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting math results by hash", e);
        }
    }

    // Добавить новый результат
    @Operation(summary = "Add a new math result", description = "Adds a new mathematical function result to the database")
    @PostMapping
    public ResponseEntity<MathRes> addMathRes(@RequestBody MathRes mathRes) {
        try {
            MathRes savedMathRes = mathService.addMathRes(mathRes);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMathRes);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding new math result", e);
        }
    }

    // Добавить несколько результатов
    @Operation(summary = "Add multiple math results", description = "Adds multiple mathematical function results to the database")
    @PostMapping("/bulk")
    public ResponseEntity<List<MathRes>> addMathResList(@RequestBody List<MathRes> mathResList) {
        try {
            List<MathRes> savedMathResList = mathService.addMathResList(mathResList);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMathResList);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding multiple math results", e);
        }
    }
}
