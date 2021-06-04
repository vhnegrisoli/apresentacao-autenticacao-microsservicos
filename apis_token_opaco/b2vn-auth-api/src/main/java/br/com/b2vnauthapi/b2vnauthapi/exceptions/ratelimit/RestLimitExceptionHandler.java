package br.com.b2vnauthapi.b2vnauthapi.exceptions.ratelimit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestLimitExceptionHandler {

    @ExceptionHandler(LimitRateException.class)
    public ResponseEntity<?> handleResouseNotFoundException(LimitRateException rfnException) {
        LimitRateExceptionDetails resourceNotFoundDetails = new LimitRateExceptionDetails();
        resourceNotFoundDetails.setError("Too Many Requests");
        resourceNotFoundDetails.setTimestamp(new Date().getTime());
        resourceNotFoundDetails.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        resourceNotFoundDetails.setMessage(rfnException.getMessage());
        return new ResponseEntity<>(resourceNotFoundDetails, HttpStatus.TOO_MANY_REQUESTS);
    }
}
