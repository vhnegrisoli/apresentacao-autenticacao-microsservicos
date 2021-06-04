package br.com.cadeiralivreempresaapi.modulos.jwt.dto;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.cadeiralivreempresaapi.modulos.comum.util.Constantes.*;
import static br.com.cadeiralivreempresaapi.modulos.jwt.utils.JwtCampoUtil.getCampo;
import static br.com.cadeiralivreempresaapi.modulos.jwt.utils.JwtCampoUtil.getCampoId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtUsuarioResponse {

    private String id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    public static JwtUsuarioResponse of(Claims dadosUsuario) {
        return JwtUsuarioResponse
            .builder()
            .id(getCampoId(dadosUsuario))
            .nome(getCampo(NOME, dadosUsuario))
            .email(getCampo(EMAIL, dadosUsuario))
            .cpf(getCampo(CPF, dadosUsuario))
            .telefone(getCampo(TELEFONE, dadosUsuario))
            .build();
    }
}
