package br.com.b2vnauthapi.b2vnauthapi.exceptions.ratelimit;

import lombok.Data;

@Data
public class LimitRateExceptionDetails {

    private long timestamp;
    private int status;
    private String error;
    private String message;

}
