package dev.shann.mcuserservice.config;

import dev.shann.mcuserservice.exceptions.EmailNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EmailNotFoundException.class})
    public ResponseEntity<Object> handleEmailNotFoundException(EmailNotFoundException emailNotFoundException, WebRequest webRequest){
        return handleExceptionInternal(emailNotFoundException,emailNotFoundException.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }
}
