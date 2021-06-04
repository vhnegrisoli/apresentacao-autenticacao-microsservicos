package br.com.biot.integracaopagarmeapi.modulos.jwt.dto;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.biot.integracaopagarmeapi.modulos.jwt.utils.JwtCampoUtil.getCampo;
import static br.com.biot.integracaopagarmeapi.modulos.jwt.utils.JwtCampoUtil.getCampoId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtUsuarioResponse {

    private String id;
    private String nome;
    private String email;
    private String cpf;

    public static JwtUsuarioResponse of(Claims dadosUsuario) {
        return JwtUsuarioResponse
            .builder()
            .id(getCampoId(dadosUsuario))
            .nome(getCampo("nome", dadosUsuario))
            .email(getCampo("email", dadosUsuario))
            .cpf(getCampo("cpf", dadosUsuario))
            .build();
    }
}
