package com.riac.teapcount.ranchapp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionRanchResponse> errorHandlerException(Exception ex) {

        ExceptionRanchResponse exceptionResponse = ExceptionRanchResponse.builder()
                .message(ex.getMessage())
                .details("Details of the Ranch Exception")
                .build();

        return new ResponseEntity<>(exceptionResponse,
                                    new HttpHeaders(),
                                    HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(RanchException.class)
    public final ResponseEntity<ExceptionRanchResponse> errorHandlerRanchNotFound(RanchException ex) {

        ExceptionRanchResponse exceptionResponse = ExceptionRanchResponse.builder()
                                                                         .message(ex.getMessage())
                                                                         .details("Details of the Ranch Exception")
                                                                         .build();
        return new ResponseEntity<>(exceptionResponse,
                                    new HttpHeaders(),
                                    HttpStatus.NOT_FOUND);
    }
}
