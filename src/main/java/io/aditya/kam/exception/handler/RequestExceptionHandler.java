package io.aditya.kam.exception.handler;

import io.aditya.kam.exception.CustomerNotFoundException;
import io.aditya.kam.exception.PointOfContactNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class RequestExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
    Map<String, String> errorMap = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> {
      errorMap.put(error.getField(), error.getDefaultMessage());
    });
    return errorMap;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(PointOfContactNotFoundException.class)
  public Map<String,String> handlePointOfContactNotFound(PointOfContactNotFoundException ex) {
    Map<String, String> errorMap = new HashMap<>();
    errorMap.put("message", ex.getMessage());
    return errorMap;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(CustomerNotFoundException.class)
  public Map<String,String> handleCustomerNotFound(CustomerNotFoundException ex) {
    Map<String, String> errorMap = new HashMap<>();
    errorMap.put("message", ex.getMessage());
    return errorMap;
  }
}
