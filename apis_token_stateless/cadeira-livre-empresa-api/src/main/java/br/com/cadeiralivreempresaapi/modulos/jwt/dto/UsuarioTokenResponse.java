package br.com.cadeiralivreempresaapi.modulos.jwt.dto;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.cadeiralivreempresaapi.modulos.jwt.utils.JwtCampoUtil.getCampoId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioTokenResponse {

    private String usuarioId;
    private String token;

    public static UsuarioTokenResponse of(Claims response, String token) {
        return UsuarioTokenResponse
            .builder()
            .usuarioId(getCampoId(response))
            .token(token)
            .build();
    }
}
