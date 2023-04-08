package com.dnc.sariapi;

import com.dnc.sariapi.models.response.SariExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@ControllerAdvice
public class SariapiApplicationException {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<SariExceptionResponse> handleResponseStatusException(ResponseStatusException rse) {
        return new ResponseEntity<>(SariExceptionResponse.builder()
                .timestamp(Instant.now().toString())
                .status(rse.getBody().getStatus())
                .error(rse.getBody().getTitle())
                .message(rse.getBody().getDetail())
                .build(), rse.getStatusCode());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<SariExceptionResponse> handleNullPointerException(NullPointerException npe) {
        return new ResponseEntity<>(SariExceptionResponse.builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(npe.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SariExceptionResponse> handleNullPointerException(Exception e) {
        return new ResponseEntity<>(SariExceptionResponse.builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
