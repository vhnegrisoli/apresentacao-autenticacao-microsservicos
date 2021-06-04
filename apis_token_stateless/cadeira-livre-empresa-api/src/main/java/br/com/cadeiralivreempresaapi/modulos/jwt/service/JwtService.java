package br.com.cadeiralivreempresaapi.modulos.jwt.service;

import br.com.cadeiralivreempresaapi.config.exception.PermissaoException;
import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.comum.util.Constantes;
import br.com.cadeiralivreempresaapi.modulos.jwt.dto.JwtUsuarioResponse;
import br.com.cadeiralivreempresaapi.modulos.jwt.dto.UsuarioTokenResponse;
import br.com.cadeiralivreempresaapi.modulos.jwt.model.UsuarioLoginJwt;
import br.com.cadeiralivreempresaapi.modulos.jwt.repository.UsuarioLoginJwtRepository;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.AutenticacaoService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.cadeiralivreempresaapi.modulos.comum.util.DataUtil.converterParaLocalDateTime;
import static br.com.cadeiralivreempresaapi.modulos.jwt.messages.JwtMessages.ERRO_DESCRIPTOGRAFAR_TOKEN;
import static br.com.cadeiralivreempresaapi.modulos.jwt.messages.JwtMessages.TOKEN_INVALIDA;
import static br.com.cadeiralivreempresaapi.modulos.jwt.utils.JwtCampoUtil.getCampoId;

@Slf4j
@Service
public class JwtService {

    @Autowired
    private UsuarioLoginJwtRepository usuarioLoginJwtRepository;
    @Autowired
    private AutenticacaoService autenticacaoService;
    @Autowired
    private Environment env;
    @Value("${jwt.secret}")
    private String secret;

    public JwtUsuarioResponse recuperarDadosDoUsuarioDoToken(String jwt) {
        try {
            if (verificarUsuarioValidoComTokenValida(jwt)) {
                return JwtUsuarioResponse.of(descriptografarJwt(jwt).getBody());
            }
            throw TOKEN_INVALIDA;
        } catch (Exception ex) {
            throw TOKEN_INVALIDA;
        }
    }

    public Boolean verificarUsuarioValidoComTokenValida(String jwt) {
        return verificarTokenValida(jwt) && validarUsuarioLogado(jwt);
    }

    public Boolean verificarTokenValida(String token) {
        try {
            var dadosUsuario = descriptografarJwt(token).getBody();
            var dataExpiracao = converterParaLocalDateTime(dadosUsuario.getExpiration());
            return dataExpiracao.isAfter(LocalDateTime.now());
        } catch (Exception ex) {
            log.error("Token inválido: ", ex);
            return false;
        }
    }

    public Jws<Claims> descriptografarJwt(String jwt) {
        try {
            return Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(getSecret().getBytes()))
                .build()
                .parseClaimsJws(jwt);
        } catch (Exception ex) {
            log.error("Erro ao tentar descriptografar token.", ex);
            throw ERRO_DESCRIPTOGRAFAR_TOKEN;
        }
    }

    @Transactional
    public UsuarioLoginJwt salvarUsuarioDoToken(UsuarioTokenResponse response, boolean tokenValida) {
        return usuarioLoginJwtRepository.save(UsuarioLoginJwt.gerarUsuario(response, tokenValida));
    }

    public Boolean validarUsuarioLogado(String jwt) {
        var dados = descriptografarJwt(jwt).getBody();
        var usuario = usuarioLoginJwtRepository.findById(getCampoId(dados))
            .orElseGet(() -> salvarUsuarioDoToken(UsuarioTokenResponse.of(dados, jwt), true));
        return usuario.isTokenValida();
    }

    @Transactional
    public void removerTokensInvalidas(Boolean precisaDeAutenticacao) {
        if (precisaDeAutenticacao && !autenticacaoService.getUsuarioAutenticado().isAdmin()) {
            throw new PermissaoException("Você não tem permissão para remover os JWTs do sistema.");
        }
        try {
            removerTokensInvalidas();
        } catch (Exception ex) {
            log.error("Erro ao tentar remover tokens inválidas.", ex);
            throw new ValidacaoException("Erro ao tentar remover tokens inválidas: ".concat(ex.getMessage()));
        }
    }

    private void removerTokensInvalidas() {
        var usuarios = buscarApenasPorTokensInvalidas();
        if (!usuarios.isEmpty()) {
            usuarioLoginJwtRepository.deleteAll(usuarios);
            log.info(String.format("Foram removidas %d tokens inválidas.", usuarios.size()));
        } else {
            log.info("Não foram encontradas tokens inválidas para remoção.");
        }
    }

    private List<UsuarioLoginJwt> buscarApenasPorTokensInvalidas() {
        return usuarioLoginJwtRepository
            .findAll()
            .stream()
            .filter(usuario -> !verificarTokenValida(usuario.getJwt()))
            .collect(Collectors.toList());
    }

    private String getSecret() {
        if (Arrays.asList(env.getActiveProfiles()).contains(Constantes.TEST_PROFILE)) {
            return Constantes.TEST_TOKEN_SECRET;
        }
        return secret;
    }
}