package br.com.cadeiralivreempresaapi.modulos.empresa.dto;

import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESituacaoUsuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ProprietarioSocioResponseTest {

    @Test
    @DisplayName("Deve converter para DTO de ProprietarioSocioResponse como proprietário quando informar Model de Usuario")
    public void of_deveConverterParaResponseSociosProprietariosComoProprietario_quandoInformarModelUsuario() {
        var usuarioProprietario = umUsuario();
        usuarioProprietario.setPermissoes(Set.of(umaPermissaoProprietario()));
        var response = ProprietarioSocioResponse.of(usuarioProprietario);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getCpf()).isEqualTo("332.368.250-57");
        assertThat(response.getEmail()).isEqualTo("usuario@gmail.com");
        assertThat(response.getNome()).isEqualTo("Usuario");
        assertThat(response.getDescricao()).isEqualTo("Proprietário");
        assertThat(response.getSituacao()).isEqualTo(ESituacaoUsuario.ATIVO);
    }

    @Test
    @DisplayName("Deve converter para DTO de ProprietarioSocioResponse como sócio quando informar Model de Usuario")
    public void of_deveConverterParaResponseSociosProprietariosComoSocio_quandoInformarDtoUsuario() {
        var usuarioSocio = umUsuario();
        usuarioSocio.setPermissoes(Set.of(umaPermissaoSocio()));
        var response = ProprietarioSocioResponse.of(usuarioSocio);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getCpf()).isEqualTo("332.368.250-57");
        assertThat(response.getEmail()).isEqualTo("usuario@gmail.com");
        assertThat(response.getNome()).isEqualTo("Usuario");
        assertThat(response.getDescricao()).isEqualTo("Sócio");
        assertThat(response.getSituacao()).isEqualTo(ESituacaoUsuario.ATIVO);
    }
}
