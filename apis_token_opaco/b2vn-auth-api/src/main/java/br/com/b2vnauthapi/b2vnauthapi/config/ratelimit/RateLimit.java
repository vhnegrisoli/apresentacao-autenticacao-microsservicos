package br.com.b2vnauthapi.b2vnauthapi.config.ratelimit;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    int value();
    String key() default "";

}