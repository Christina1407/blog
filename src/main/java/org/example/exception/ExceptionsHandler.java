package org.example.exception;

import org.example.controller.CommentController;
import org.example.controller.PostController;
import org.example.controller.UserController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice(assignableTypes = {UserController.class, PostController.class, CommentController.class})
public class ExceptionsHandler {

  @ExceptionHandler(NotFoundException.class)
    public ExceptionResponse handleNotFoundException(NotFoundException ex) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND.name(), "The required object was not found", ex.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler(ConflictException.class)
    public ExceptionResponse handleConflictException(final ConflictException ex) {
        return new ExceptionResponse(HttpStatus.CONFLICT.name(), ex.getReason(), ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.name(), "Incorrectly made request",
                errorMessage, LocalDateTime.now());
    }
}
