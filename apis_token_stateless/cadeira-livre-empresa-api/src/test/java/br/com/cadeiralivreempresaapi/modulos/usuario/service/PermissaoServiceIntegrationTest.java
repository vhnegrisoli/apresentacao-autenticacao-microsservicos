package br.com.cadeiralivreempresaapi.modulos.usuario.service;

import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@Import(PermissaoService.class)
@Sql(scripts = {"classpath:/usuarios_tests.sql"})
public class PermissaoServiceIntegrationTest {

    @Autowired
    private PermissaoService service;

    @Test
    @DisplayName("Deve buscar permissão por ID quando for encontrada")
    public void buscarPorId_deveRetornarPermissao_quandoEncontrado() {
        var permissao = service.buscarPorId(1);
        assertThat(permissao).isNotNull();
        assertThat(permissao.getId()).isEqualTo(1);
        assertThat(permissao.getDescricao()).isEqualTo("Administrador");
        assertThat(permissao.getPermissao()).isEqualTo(EPermissao.ADMIN);
    }

    @Test
    @DisplayName("Deve lançar exception quando não encontrar permissão por ID")
    public void buscarPorId_deveLancarException_quandoNaoForEncontrado() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.buscarPorId(1000))
            .withMessage("A permissão não foi encontrada.");
    }

    @Test
    @DisplayName("Deve buscar permissão por enum permissão quando for encontrada")
    public void buscarPorCodigo_deveRetornarPermissao_quandoEncontrado() {
        var permissao = service.buscarPorCodigo(EPermissao.ADMIN);
        assertThat(permissao).isNotNull();
        assertThat(permissao.getId()).isEqualTo(1);
        assertThat(permissao.getDescricao()).isEqualTo("Administrador");
        assertThat(permissao.getPermissao()).isEqualTo(EPermissao.ADMIN);
    }

    @Test
    @DisplayName("Deve lançar exception quando não encontrar permissão por enum permissão")
    public void buscarPorCodigo_deveLancarException_quandoNaoForEncontrado() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.buscarPorCodigo(EPermissao.PERMISSAO_NAO_MAPEADA))
            .withMessage("A permissão não foi encontrada.");
    }
}
