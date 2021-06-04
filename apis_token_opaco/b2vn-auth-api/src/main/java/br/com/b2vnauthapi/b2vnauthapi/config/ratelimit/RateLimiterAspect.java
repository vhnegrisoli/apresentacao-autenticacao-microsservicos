package br.com.b2vnauthapi.b2vnauthapi.config.ratelimit;

import br.com.b2vnauthapi.b2vnauthapi.exceptions.ratelimit.LimitRateException;
import br.com.b2vnauthapi.b2vnauthapi.modules.usuario.service.UsuarioService;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.springframework.util.ObjectUtils.isEmpty;

@Aspect
@Component
public class RateLimiterAspect {

    private static final Integer RATE_LIMIT = 5;

    public interface KeyFactory {
        String createKey(JoinPoint jp, Integer limit);
    }

    @Autowired
    private UsuarioService usuarioService;

    private static final KeyFactory DEFAULT_KEY_FACTORY = (jp, limit) -> JoinPointToStringHelper.toString(jp);

    private final ConcurrentHashMap<String, RateLimiter> limiters;

    private final KeyFactory keyFactory;

    @Autowired
    public RateLimiterAspect(Optional<KeyFactory> keyFactory) {
        this.limiters = new ConcurrentHashMap<>();
        this.keyFactory = keyFactory.orElse(DEFAULT_KEY_FACTORY);
    }

    @Before("@annotation(limit)")
    public void rateLimit(JoinPoint jp, RateLimit limit) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer rateLimit = RATE_LIMIT;
        if (!isEmpty(principal)) {
            var usuarioLogado = usuarioService.getUsuarioAutenticado();
            var usuario = usuarioService.buscarUm(usuarioLogado.getId());
            rateLimit = usuario.getPermissao().getRateLimit();
        }
        String key = createKey(jp, rateLimit);
        RateLimiter limiter = limiters.computeIfAbsent(key, createLimiter(rateLimit));
        var aquire = limiter.tryAcquire(0, TimeUnit.SECONDS);
        if (!aquire) {
            throw new LimitRateException("Você fez muitas solicitações, aguarde.");
        }
    }

    private Function<String, RateLimiter> createLimiter(int limit) {
        return name -> RateLimiter.create(limit);
    }

    private String createKey(JoinPoint jp, Integer limit) {
        return Optional.ofNullable(Strings.emptyToNull(limit.toString()))
            .orElseGet(() -> keyFactory.createKey(jp, limit));
    }
}
