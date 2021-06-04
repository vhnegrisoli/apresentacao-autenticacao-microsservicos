package br.com.cadeiralivreempresaapi.modulos.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import static br.com.cadeiralivreempresaapi.modulos.comum.util.Constantes.TEST_TOKEN_SECRET;
import static br.com.cadeiralivreempresaapi.modulos.jwt.mocks.JwtMocks.gerarMockMapUsuario;

public class JwtTestUtil {

    private static final Integer CINCO_HORAS = 18000000;

    public static String gerarTokenTeste() {
        var uuid = "5cd48099-1009-43c4-b979-f68148a2a81d";
        var dados = gerarMockMapUsuario(uuid);
        return Jwts
            .builder()
            .setClaims(dados)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + CINCO_HORAS))
            .signWith(Keys.hmacShaKeyFor(TEST_TOKEN_SECRET.getBytes()))
            .compact();
    }

    public static String gerarTokenExpirado() {
        var uuid = "5cd48099-1009-43c4-b979-f68148a2a81d";
        var dados = gerarMockMapUsuario(uuid);
        return Jwts
            .builder()
            .setClaims(dados)
            .setIssuedAt(new Date(System.currentTimeMillis() - CINCO_HORAS))
            .setExpiration(new Date(System.currentTimeMillis()))
            .signWith(Keys.hmacShaKeyFor(TEST_TOKEN_SECRET.getBytes()))
            .compact();
    }

    public static Claims recuperarBody(String jwt) {
        return Jwts
            .parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(TEST_TOKEN_SECRET.getBytes()))
            .build()
            .parseClaimsJws(jwt)
            .getBody();
    }
}
