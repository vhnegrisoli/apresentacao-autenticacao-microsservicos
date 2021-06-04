package br.com.cadeiralivreempresaapi.modulos.usuario.dto;

import lombok.Data;

@Data
public class TokenResponse {

    private static final String BEARER = "bearer ";
    private static final String EMPTY = "";

    private String token;
    private String bearerToken;

    public TokenResponse(String token) {
        this.bearerToken = token;
        token = token.toLowerCase();
        this.token = token.replace(BEARER, EMPTY);
    }
}
