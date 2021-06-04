package br.com.b2vnauthapi.b2vnauthapi.exceptions.ratelimit;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class LimitRateException extends RuntimeException {

    public LimitRateException(String message) {
        super(message);
    }
}
