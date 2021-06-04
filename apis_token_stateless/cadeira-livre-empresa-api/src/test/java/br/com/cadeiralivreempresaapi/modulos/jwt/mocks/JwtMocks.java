package br.com.cadeiralivreempresaapi.modulos.jwt.mocks;

import br.com.cadeiralivreempresaapi.modulos.jwt.dto.JwtUsuarioResponse;
import br.com.cadeiralivreempresaapi.modulos.jwt.dto.UsuarioTokenResponse;
import br.com.cadeiralivreempresaapi.modulos.jwt.model.UsuarioLoginJwt;

import java.util.HashMap;
import java.util.Map;

import static br.com.cadeiralivreempresaapi.modulos.jwt.util.JwtTestUtil.gerarTokenTeste;

public class JwtMocks {

    private static final String JWT = gerarTokenTeste();

    public static UsuarioLoginJwt umUsuarioLoginJwt(Boolean valida) {
        return UsuarioLoginJwt.gerarUsuario(umUsuarioTokenResponse(), valida);
    }

    public static JwtUsuarioResponse umJwtUsuarioResponse() {
        return JwtUsuarioResponse
            .builder()
            .id("5cd48099-1009-43c4-b979-f68148a2a81d")
            .nome("Victor Hugo Negrisoli")
            .email("vhnegrisoli@gmail.com")
            .cpf("103.324.589-54")
            .build();
    }

    public static UsuarioTokenResponse umUsuarioTokenResponse() {
        return UsuarioTokenResponse
            .builder()
            .usuarioId("5cd48099-1009-43c4-b979-f68148a2a81d")
            .token(JWT)
            .build();
    }

    public static Map<String, Object> gerarMockMapUsuario(String uuid) {
        var usuario = new HashMap<String, Object>();
        usuario.put("id", uuid);
        usuario.put("nome", "Victor Hugo Negrisoli");
        usuario.put("email", "vhnegrisoli@gmail.com");
        usuario.put("cpf", "103.324.589-54");
        return usuario;
    }
}
