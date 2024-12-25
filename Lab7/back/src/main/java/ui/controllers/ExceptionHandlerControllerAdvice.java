package ui.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ui.dto.BasedExceptionDTO;
import ui.exeptions.BasedException;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice{
    @ExceptionHandler(BasedException.class)
    public ResponseEntity<BasedExceptionDTO> uiException(BasedException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BasedExceptionDTO(
                exception.getMessage(), exception.getClass().getSimpleName()
        ));
    }
}
