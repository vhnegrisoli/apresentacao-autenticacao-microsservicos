package br.com.cadeiralivreempresaapi.modulos.jwt.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.cadeiralivreempresaapi.modulos.jwt.util.JwtTestUtil.gerarTokenTeste;
import static br.com.cadeiralivreempresaapi.modulos.jwt.util.JwtTestUtil.recuperarBody;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UsuarioTokenResponseTest {

    @Test
    @DisplayName("Deve converter para objeto de response quando informar claims e token")
    public void of_deveConverterParaResponse_quandoInformarClaimsEToken() {
        var token = gerarTokenTeste();
        var dados = recuperarBody(token);
        var usuarioTokenResponse = UsuarioTokenResponse.of(dados, token);
        assertThat(usuarioTokenResponse).isNotNull();
        assertThat(usuarioTokenResponse.getUsuarioId()).isEqualTo("5cd48099-1009-43c4-b979-f68148a2a81d");
    }
}
