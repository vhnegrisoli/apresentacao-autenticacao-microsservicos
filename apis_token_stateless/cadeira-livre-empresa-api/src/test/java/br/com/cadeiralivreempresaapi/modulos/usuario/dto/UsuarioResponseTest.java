package br.com.cadeiralivreempresaapi.modulos.usuario.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.umUsuario;
import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.umaPermissaoAdmin;
import static org.assertj.core.api.Assertions.assertThat;

public class UsuarioResponseTest {

    @Test
    @DisplayName("Deve converter para dto UsuarioResponse quando informado Model de Usuario")
    public void of_deveConverterParaDtoDeResponse_quandoReceberModelDeUsuario() {
        var response = UsuarioResponse.of(umUsuario());
        assertThat(response).isNotNull();
        assertThat(response.getNome()).isEqualTo("Usuario");
        assertThat(response.getEmail()).isEqualTo("usuario@gmail.com");
        assertThat(response.getCpf()).isEqualTo("332.368.250-57");
        assertThat(response.getPermissoes()).isEqualTo(Set.of(umaPermissaoAdmin()));
    }
}
