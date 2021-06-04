package br.com.biot.integracaopagarmeapi.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class JsonExceptionHandler {

    @ExceptionHandler(AutenticacaoException.class)
    public ResponseEntity<?> handleAutenticacaoException(AutenticacaoException autenticacaoException) {
        ExceptionDetails autenticacaoExceptionDetails = new ExceptionDetails();
        autenticacaoExceptionDetails.setStatus(HttpStatus.UNAUTHORIZED.value());
        autenticacaoExceptionDetails.setMessage(autenticacaoException.getMessage());
        return new ResponseEntity<>(autenticacaoExceptionDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<?> handleValidacaoException(ValidacaoException validacaoException) {
        ExceptionDetails validacaoExceptionDetails = new ExceptionDetails();
        validacaoExceptionDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        validacaoExceptionDetails.setMessage(validacaoException.getMessage());
        return new ResponseEntity<>(validacaoExceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperacaoProibidaException.class)
    public ResponseEntity<?> handleOperacaoProibidaException(OperacaoProibidaException operacaoProibidaException) {
        ExceptionDetails operacaoProibidaExceptionHandlerDetails = new ExceptionDetails();
        operacaoProibidaExceptionHandlerDetails.setStatus(HttpStatus.FORBIDDEN.value());
        operacaoProibidaExceptionHandlerDetails.setMessage(operacaoProibidaException.getMessage());
        return new ResponseEntity<>(operacaoProibidaExceptionHandlerDetails, HttpStatus.FORBIDDEN);
    }
}
