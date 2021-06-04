package br.com.cadeiralivreempresaapi.modulos.empresa.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.umUsuario;
import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.umaPermissaoProprietario;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProprietarioSocioClienteResponseTest {

    @Test
    @DisplayName("Deve retornar objeto de response como proprietário quando informar um usuário proprietário")
    public void of_deveRetornarObjetoResponseComoProprietario_quandoInformarUsuarioProprietario() {
        var usuario = umUsuario();
        usuario.setPermissoes(Set.of(umaPermissaoProprietario()));
        var response = ProprietarioSocioClienteResponse.of(usuario);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNome()).isEqualTo("Usuario");
        assertThat(response.getDescricao()).isEqualTo("Proprietário");
    }

    @Test
    @DisplayName("Deve retornar objeto de response como sócio quando informar um usuário sócio")
    public void of_deveRetornarObjetoResponseComoSocio_quandoInformarUsuarioSocio() {
        var response = ProprietarioSocioClienteResponse.of(umUsuario());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNome()).isEqualTo("Usuario");
        assertThat(response.getDescricao()).isEqualTo("Sócio");
    }
}
