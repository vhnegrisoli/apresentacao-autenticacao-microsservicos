package br.com.cadeiralivreempresaapi.modulos.empresa.model;

import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ESituacaoEmpresa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static br.com.cadeiralivreempresaapi.modulos.empresa.mocks.EmpresaMocks.umaEmpresa;
import static br.com.cadeiralivreempresaapi.modulos.empresa.mocks.EmpresaMocks.umaEmpresaRequest;
import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.umUsuario;
import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.umaPermissaoSocio;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class EmpresaTest {

    @Test
    @DisplayName("Deve retornar true se empresa estiver ativa")
    public void isAtiva_deveRetornarTrue_seSituacaoForAtiva() {
        assertThat(umaEmpresa().isAtiva()).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false se empresa não estiver ativa")
    public void isAtiva_deveRetornarFalse_seSituacaoNaoForAtiva() {
        var empresa = umaEmpresa();
        empresa.setSituacao(ESituacaoEmpresa.INATIVA);
        assertThat(empresa.isAtiva()).isFalse();
    }

    @Test
    @DisplayName("Deve retornar Model de Empresa com tempo 30s quando informar DTO de Empresa Request sem tempo")
    public void of_deveRetornarModelEmpresaComTempo30Segundos_quandoInformarObjetoDtoEmpresaRequestSemTempo() {
        var request = umaEmpresaRequest();
        request.setTempoRefreshCadeiraLivre(null);
        var empresa = Empresa.of(request);

        assertThat(empresa).isNotNull();
        assertThat(empresa.getId()).isEqualTo(1);
        assertThat(empresa.getNome()).isEqualTo("Empresa 01");
        assertThat(empresa.getRazaoSocial()).isEqualTo("Empresa 01");
        assertThat(empresa.getCpfCnpj()).isEqualTo("82.765.926/0001-32");
        assertThat(empresa.getTempoRefreshCadeiraLivre()).isEqualTo(30);
    }

    @Test
    @DisplayName("Deve retornar Model de Empresa com tempo especificado quando informar DTO de Empresa Request com tempo")
    public void of_deveRetornarModelEmpresaComTempoEspecificado_quandoInformarObjetoDtoEmpresaRequestComTempo() {
        var request = umaEmpresaRequest();
        var empresa = Empresa.of(request);

        assertThat(empresa).isNotNull();
        assertThat(empresa.getId()).isEqualTo(1);
        assertThat(empresa.getNome()).isEqualTo("Empresa 01");
        assertThat(empresa.getRazaoSocial()).isEqualTo("Empresa 01");
        assertThat(empresa.getCpfCnpj()).isEqualTo("82.765.926/0001-32");
        assertThat(empresa.getTempoRefreshCadeiraLivre()).isEqualTo(10);
    }

    @Test
    @DisplayName("Deve adicionar mais um sócio caso já exista um salvo para a empresa")
    public void adicionarProprietario_deveAdicionarMaisUmSocio_quandoEmpresaJaPossuirSocio() {
        var empresa = umaEmpresa();
        var socio = umUsuario();
        socio.setPermissoes(Set.of(umaPermissaoSocio()));
        assertThat(empresa.getSocios()).isNotNull();
        assertThat(empresa.getSocios().size()).isEqualTo(1);
        empresa.adicionarProprietario(socio);
        assertThat(empresa.getSocios().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve criar lista de sócio caso não exista um salvo para a empresa")
    public void adicionarProprietario_deveCriarListaSocios_quandoEmpresaNaoPossuirSocio() {
        var empresa = umaEmpresa();
        empresa.setSocios(null);
        assertThat(empresa.getSocios()).isNull();
        var socio = umUsuario();
        socio.setPermissoes(Set.of(umaPermissaoSocio()));
        empresa.adicionarProprietario(socio);
        assertThat(empresa.getSocios().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Deve lançar exception quando usuário não for sócio e nem preopritário")
    public void validarPermissaoUsuarioProprietario_deveLancarException_quandoUsuarioNaoForSocioProprietario() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> umaEmpresa().adicionarProprietario(umUsuario()))
            .withMessage("Para salvar uma empresa, o usuário deve ser um proprietário.");
    }

}
