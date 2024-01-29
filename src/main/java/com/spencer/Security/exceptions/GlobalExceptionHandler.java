package com.spencer.Security.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public @ResponseBody ResponseEntity<ErrorObject> handleAuthenticationException( AuthenticationException ex,

                                                                                         HttpServletResponse response) {
        ErrorObject errorObject = new ErrorObject();
         errorObject.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());
        return new ResponseEntity<>(errorObject, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public @ResponseBody ResponseEntity<ErrorObject> handleAccessDeniedException( AccessDeniedException ex,

                                                                                    HttpServletResponse response) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.FORBIDDEN.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());
        return new ResponseEntity<>(errorObject, HttpStatus.FORBIDDEN);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("statusCode",HttpStatus.BAD_REQUEST);
        result.put("message","Validation failed");
        result.put("timestamp",new Date());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        result.put("errors",errors);
        return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
    }


/*    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                                    HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        result.put("statusCode",HttpStatus.BAD_REQUEST);
        result.put("message",ex.getMessage());
        result.put("timestamp",new Date());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        result.put("errors",errors);
        return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
    }*/



}
