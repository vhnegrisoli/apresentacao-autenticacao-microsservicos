package br.com.cadeiralivreempresaapi.modulos.jwt.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.cadeiralivreempresaapi.modulos.jwt.util.JwtTestUtil.gerarTokenTeste;
import static br.com.cadeiralivreempresaapi.modulos.jwt.util.JwtTestUtil.recuperarBody;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JwtUsuarioResponseTest {

    @Test
    @DisplayName("Deve converter para objeto de response quando informar claims")
    public void of_deveConverterParaResponse_quandoInformarClaims() {
        var jwtUsuarioResponse = JwtUsuarioResponse.of(recuperarBody(gerarTokenTeste()));
        assertThat(jwtUsuarioResponse).isNotNull();
        assertThat(jwtUsuarioResponse.getId()).isEqualTo("5cd48099-1009-43c4-b979-f68148a2a81d");
        assertThat(jwtUsuarioResponse.getNome()).isEqualTo("Victor Hugo Negrisoli");
        assertThat(jwtUsuarioResponse.getEmail()).isEqualTo("vhnegrisoli@gmail.com");
        assertThat(jwtUsuarioResponse.getCpf()).isEqualTo("103.324.589-54");
    }
}
