package br.com.cadeiralivreempresaapi.modulos.empresa.service;

import br.com.cadeiralivreempresaapi.config.exception.PermissaoException;
import br.com.cadeiralivreempresaapi.modulos.agenda.service.HorarioService;
import br.com.cadeiralivreempresaapi.modulos.agenda.service.ServicoService;
import br.com.cadeiralivreempresaapi.modulos.comum.dto.PageRequest;
import br.com.cadeiralivreempresaapi.modulos.empresa.dto.EmpresaFiltros;
import br.com.cadeiralivreempresaapi.modulos.empresa.dto.ProprietarioSocioClienteResponse;
import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ESituacaoEmpresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ETipoEmpresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.repository.EmpresaRepository;
import br.com.cadeiralivreempresaapi.modulos.jwt.service.JwtService;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.AutenticacaoService;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static br.com.cadeiralivreempresaapi.modulos.jwt.util.JwtTestUtil.gerarTokenTeste;
import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
@Import({EmpresaService.class, JwtService.class})
@ExtendWith(MockitoExtension.class)
@Sql(scripts = {
    "classpath:/usuarios_tests.sql",
    "classpath:/funcionarios_tests.sql"
})
public class EmpresaServiceIntegrationTest {

    @Autowired
    private EmpresaService service;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private JwtService jwtService;
    @MockBean
    private UsuarioService usuarioService;
    @MockBean
    private AutenticacaoService autenticacaoService;
    @MockBean
    private ServicoService servicoService;
    @MockBean
    private HorarioService horarioService;
    @MockBean
    private EnderecoService enderecoService;

    @Test
    @DisplayName("Deve salvar empresa quando dados estiverem corretos")
    public void buscarPorId_deveRetornarEmpresa_quandoEncontrarPorIdEUsuarioPossuirPermissao() {
        var usuarioAutenticado = umUsuarioAutenticadoSocio();
        usuarioAutenticado.setId(6);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(usuarioAutenticado);
        var empresa = service.buscarPorId(4);
        assertThat(empresa).isNotNull();
        assertThat(empresa.getId()).isEqualTo(4);
        assertThat(empresa.getSocios().size()).isEqualTo(2);
        assertThat(empresa.getNome()).isEqualTo("Empresa 01 Edicao");
        assertThat(empresa.getRazaoSocial()).isEqualTo("Empresa 01");
        assertThat(empresa.getCpfCnpj()).isEqualTo("26.343.835/0001-38");
        assertThat(empresa.getTipoEmpresa()).isEqualTo(ETipoEmpresa.SALAO);
        assertThat(empresa.getSituacao()).isEqualTo(ESituacaoEmpresa.ATIVA);
    }

    @Test
    @DisplayName("Deve salvar empresa quando dados estiverem corretos")
    public void buscarPorId_deveLancarException_quandoUsaurioNaoPossuirPermissao() {
        var usuarioAutenticado = umUsuarioAutenticadoSocio();
        usuarioAutenticado.setId(3);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(usuarioAutenticado);

        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.buscarPorId(1000))
            .withMessage("Usuário sem permissão para visualizar essa empresa.");
    }

    @Test
    @DisplayName("Deve salvar empresa quando dados estiverem corretos")
    public void buscarPorIdComSocios_deveRetornarEmpresaComSocios_quandoEncontrarPorIdEUsuarioPossuirPermissao() {
        var usuarioAutenticado = umUsuarioAutenticadoSocio();
        usuarioAutenticado.setId(6);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(usuarioAutenticado);
        var response = service.buscarPorIdComSocios(4);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(4);
        assertThat(response.getProprietarioSocios().size()).isEqualTo(2);
        assertThat(response.getProprietarioSocios().get(0).getNome()).isEqualTo("Proprietario 1");
        assertThat(response.getProprietarioSocios().get(1).getNome()).isEqualTo("Sócio 1 Update");
        assertThat(response.getNome()).isEqualTo("Empresa 01 Edicao");
        assertThat(response.getRazaoSocial()).isEqualTo("Empresa 01");
        assertThat(response.getCpfCnpj()).isEqualTo("26.343.835/0001-38");
        assertThat(response.getTipoEmpresa()).isEqualTo(ETipoEmpresa.SALAO);
        assertThat(response.getSituacao()).isEqualTo(ESituacaoEmpresa.ATIVA);
    }

    @Test
    @DisplayName("Deve salvar empresa quando dados estiverem corretos")
    public void buscarPorIdComSocios_deveLancarException_quandoUsaurioNaoPossuirPermissao() {
        var usuarioAutenticado = umUsuarioAutenticadoSocio();
        usuarioAutenticado.setId(3);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(usuarioAutenticado);

        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.buscarPorIdComSocios(1000))
            .withMessage("Usuário sem permissão para visualizar essa empresa.");
    }

    @Test
    @DisplayName("Deve retornar todas paginadas sem filtros quando usuario for admin")
    public void buscarTodas_deveRetornarTodasPaginadasSemFiltros_quandoUsuarioForAdmin() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoAdmin());

        assertThat(service.buscarTodas(new PageRequest(), new EmpresaFiltros()))
            .extracting("id", "cpfCnpj", "nome", "tipoEmpresa", "situacao")
            .containsExactly(
                tuple(4, "26.343.835/0001-38", "Empresa 01 Edicao", ETipoEmpresa.SALAO, ESituacaoEmpresa.ATIVA),
                tuple(7, "49.579.794/0001-89", "Empresa 02", ETipoEmpresa.SALAO, ESituacaoEmpresa.ATIVA)
            );
    }

    @Test
    @DisplayName("Deve retornar todas paginadas sem filtros quando usuario for proprietário")
    public void buscarTodas_deveRetornarTodasPaginadasSemFiltros_quandoUsuarioForProprietario() {
        var proprietario = umUsuarioAutenticadoProprietario();
        proprietario.setId(3);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(proprietario);

        assertThat(service.buscarTodas(new PageRequest(), new EmpresaFiltros()))
            .extracting("id", "cpfCnpj", "nome", "tipoEmpresa", "situacao")
            .containsExactly(
                tuple(7, "49.579.794/0001-89", "Empresa 02", ETipoEmpresa.SALAO, ESituacaoEmpresa.ATIVA)
            );
    }

    @Test
    @DisplayName("Deve retornar todas paginadas sem filtros quando usuario for sócio")
    public void buscarTodas_deveRetornarTodasPaginadasSemFiltros_quandoUsuarioForSocio() {
        var proprietario = umUsuarioAutenticadoSocio();
        proprietario.setId(8);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(proprietario);

        assertThat(service.buscarTodas(new PageRequest(), new EmpresaFiltros()))
            .extracting("id", "cpfCnpj", "nome", "tipoEmpresa", "situacao")
            .containsExactly(
                tuple(7, "49.579.794/0001-89", "Empresa 02", ETipoEmpresa.SALAO, ESituacaoEmpresa.ATIVA)
            );
    }

    @Test
    @DisplayName("Deve retornar true quando existir uma empresa para o usuário")
    public void existeEmpresaParaUsuario_deveRetornarTrue_quandoExistirEmpresaParaUmUsuario() {
        assertThat(service.existeEmpresaParaUsuario(4, 2)).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando não existir uma empresa para o usuário")
    public void existeEmpresaParaUsuario_deveRetornarFalse_quandoNaoExistirEmpresaParaUmUsuario() {
        assertThat(service.existeEmpresaParaUsuario(4, 7)).isFalse();
    }

    @Test
    @DisplayName("Deve ativar empresa quando empresa estiver inativa e usuário possuir permissão sendo admin")
    public void alterarSituacao_deveAtivarEmpresa_quandoUsuarioAdminPossirPermissaoEEmpresaEstiverInativa() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoAdmin());

        alterarSituacao(4, ESituacaoEmpresa.INATIVA);
        var empresaInativa = empresaRepository.findById(4).get();
        assertThat(empresaInativa.isAtiva()).isFalse();

        var response = service.alterarSituacao(4);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("A situação da empresa foi alterada com sucesso!");

        var empresaAtiva = empresaRepository.findById(4).get();
        assertThat(empresaAtiva.isAtiva()).isTrue();
    }

    @Test
    @DisplayName("Deve ativar empresa quando empresa estiver inativa e usuário possuir permissão sendo proprietário")
    public void alterarSituacao_deveAtivarEmpresa_quandoUsuarioProprietarioPossirPermissaoEEmpresaEstiverInativa() {
        var proprietario = umUsuarioAutenticadoProprietario();
        proprietario.setId(2);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(proprietario);

        alterarSituacao(4, ESituacaoEmpresa.INATIVA);
        var empresaInativa = empresaRepository.findById(4).get();
        assertThat(empresaInativa.isAtiva()).isFalse();

        var response = service.alterarSituacao(4);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("A situação da empresa foi alterada com sucesso!");

        var empresaAtiva = empresaRepository.findById(4).get();
        assertThat(empresaAtiva.isAtiva()).isTrue();
    }

    @Test
    @DisplayName("Deve ativar empresa quando empresa estiver inativa e usuário possuir permissão sendo proprietário")
    public void alterarSituacao_deveInativarEmpresa_quandoUsuarioProprietarioPossirPermissaoEEmpresaEstiverAtiva() {
        var proprietario = umUsuarioAutenticadoProprietario();
        proprietario.setId(2);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(proprietario);

        var empresaAtiva = empresaRepository.findById(4).get();
        assertThat(empresaAtiva.isAtiva()).isTrue();

        var response = service.alterarSituacao(4);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("A situação da empresa foi alterada com sucesso!");

        var empresaInativa = empresaRepository.findById(4).get();
        assertThat(empresaAtiva.isAtiva()).isFalse();
    }

    @Test
    @DisplayName("Deve ativar empresa quando empresa estiver inativa e usuário possuir permissão sendo sócio")
    public void alterarSituacao_deveAtivarEmpresa_quandoUsuarioSocioPossirPermissaoEEmpresaEstiverInativa() {
        var socio = umUsuarioAutenticadoSocio();
        socio.setId(6);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(socio);

        alterarSituacao(4, ESituacaoEmpresa.INATIVA);
        var empresaInativa = empresaRepository.findById(4).get();
        assertThat(empresaInativa.isAtiva()).isFalse();

        var response = service.alterarSituacao(4);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("A situação da empresa foi alterada com sucesso!");

        var empresaAtiva = empresaRepository.findById(4).get();
        assertThat(empresaAtiva.isAtiva()).isTrue();
    }

    @Test
    @DisplayName("Deve lançar exception quando usuário proprietário sem permissão tentar alterar a situação")
    public void alterarSituacao_deveLancarException_quandoUsuarioProprietarioNaoPossuirPermissao() {
        var proprietario = umUsuarioAutenticadoProprietario();
        proprietario.setId(3);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(proprietario);

        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.alterarSituacao(4))
            .withMessage("Usuário sem permissão para visualizar essa empresa.");
    }

    @Test
    @DisplayName("Deve lançar exception quando usuário sócio sem permissão tentar alterar a situação")
    public void alterarSituacao_deveLancarException_quandoUsuarioSocioNaoPossuirPermissao() {
        var socio = umUsuarioAutenticadoSocio();
        socio.setId(8);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(socio);

        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.alterarSituacao(4))
            .withMessage("Usuário sem permissão para visualizar essa empresa.");
    }

    @Test
    @DisplayName("Deve buscar empresas quando informar token válida")
    public void buscarEmpresasParaCliente_deveBuscarEmpresas_quandoInformarTokenValida() {
        var token = gerarTokenTeste();
        assertThat(service.buscarEmpresasParaCliente(token, new EmpresaFiltros()))
            .extracting("id", "nome", "cpfCnpj", "tipoEmpresa")
            .containsExactly(
                tuple(4, "Empresa 01 Edicao", "26.343.835/0001-38", "Salão de Beleza"),
                tuple(7, "Empresa 02", "49.579.794/0001-89", "Salão de Beleza")
            );
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar buscar empresas com token inválida.")
    public void buscarEmpresasParaCliente_deveLancarException_quandoTokenForInvalida() {
        var token = gerarTokenTeste() + "123";
        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.buscarEmpresasParaCliente(token, new EmpresaFiltros()))
            .withMessage("O usuário não está autenticado.");
    }

    @Test
    @DisplayName("Deve buscar empresa por ID quando informar token válida")
    public void buscarEmpresaPorId_deveBuscarEmpresaPorId_quandoInformarTokenValida() {
        var token = gerarTokenTeste();
        var empresa = service.buscarEmpresaPorId(4, token);
        assertThat(empresa).isNotNull();
        assertThat(empresa.getId()).isEqualTo(4);
        assertThat(empresa.getNome()).isEqualTo("Empresa 01 Edicao");
        assertThat(empresa.getRazaoSocial()).isEqualTo("Empresa 01");
        assertThat(empresa.getCpfCnpj()).isEqualTo("26.343.835/0001-38");
        assertThat(empresa.getTipoEmpresa()).isEqualTo("Salão de Beleza");
        assertThat(empresa.getProprietarioSocios()).isEqualTo(List.of(
            new ProprietarioSocioClienteResponse(2, "Proprietario 1", "Proprietário"),
            new ProprietarioSocioClienteResponse(6, "Sócio 1 Update", "Sócio")
        ));
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar buscar empresa por ID com token inválida.")
    public void buscarEmpresaPorId_deveLancarException_quandoTokenForInvalida() {
        var token = gerarTokenTeste() + "123";
        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.buscarEmpresaPorId(4, token))
            .withMessage("O usuário não está autenticado.");
    }

    private void alterarSituacao(Integer empresaId, ESituacaoEmpresa situacao) {
        var empresa = empresaRepository.findById(empresaId).get();
        empresa.setSituacao(situacao);
        empresaRepository.save(empresa);
    }
}
