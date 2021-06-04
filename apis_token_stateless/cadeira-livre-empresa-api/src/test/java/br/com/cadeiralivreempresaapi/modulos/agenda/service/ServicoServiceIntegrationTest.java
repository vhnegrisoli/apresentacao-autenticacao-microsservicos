package br.com.cadeiralivreempresaapi.modulos.agenda.service;

import br.com.cadeiralivreempresaapi.config.exception.PermissaoException;
import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.agenda.repository.AgendaRepository;
import br.com.cadeiralivreempresaapi.modulos.agenda.repository.ServicoRepository;
import br.com.cadeiralivreempresaapi.modulos.empresa.service.EmpresaService;
import br.com.cadeiralivreempresaapi.modulos.empresa.service.EnderecoService;
import br.com.cadeiralivreempresaapi.modulos.funcionario.repository.FuncionarioRepository;
import br.com.cadeiralivreempresaapi.modulos.funcionario.service.FuncionarioService;
import br.com.cadeiralivreempresaapi.modulos.jwt.service.JwtService;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.AutenticacaoService;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.PermissaoService;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.UsuarioAcessoService;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
@Import({
    ServicoService.class,
    EmpresaService.class,
    UsuarioService.class,
    UsuarioAcessoService.class,
    FuncionarioService.class,
    JwtService.class
})
@ExtendWith(MockitoExtension.class)
@Sql(scripts = {
    "classpath:/usuarios_tests.sql",
    "classpath:/funcionarios_tests.sql",
    "classpath:/agendas_tests.sql"
})
public class ServicoServiceIntegrationTest {

    @Autowired
    private ServicoService service;
    @Autowired
    private ServicoRepository repository;
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private UsuarioAcessoService acessoService;
    @MockBean
    private AutenticacaoService autenticacaoService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private PermissaoService permissaoService;
    @MockBean
    private HorarioService horarioService;
    @MockBean
    private EnderecoService enderecoService;

    @Test
    @DisplayName("Deve buscar serviços por ID quando usuário for admin")
    public void buscarServicoPorId_deveBuscarServico_quandoInformarIdSendoAdmin() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoAdmin());

        var response = service.buscarServicoPorId(1);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getDescricao()).isEqualTo("Corte exclusivo");
        assertThat(response.getPreco()).isEqualTo(15.90);
        assertThat(response.getEmpresa()).isEqualTo("Empresa 01 Edicao");
        assertThat(response.getCpfCnpj()).isEqualTo("26.343.835/0001-38");
    }

    @Test
    @DisplayName("Deve buscar serviços por ID quando usuário for proprietário")
    public void buscarServicoPorId_deveBuscarServico_quandoInformarIdSendoProprietario() {
        var proprietario = umUsuarioAutenticadoProprietario();
        proprietario.setId(2);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(proprietario);

        var response = service.buscarServicoPorId(1);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getDescricao()).isEqualTo("Corte exclusivo");
        assertThat(response.getPreco()).isEqualTo(15.90);
        assertThat(response.getEmpresa()).isEqualTo("Empresa 01 Edicao");
        assertThat(response.getCpfCnpj()).isEqualTo("26.343.835/0001-38");
    }

    @Test
    @DisplayName("Deve buscar serviços por ID quando usuário for sócio")
    public void buscarServicoPorId_deveBuscarServico_quandoInformarIdSendoSocio() {
        var socio = umUsuarioAutenticadoSocio();
        socio.setId(6);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(socio);

        var response = service.buscarServicoPorId(1);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getDescricao()).isEqualTo("Corte exclusivo");
        assertThat(response.getPreco()).isEqualTo(15.90);
        assertThat(response.getEmpresa()).isEqualTo("Empresa 01 Edicao");
        assertThat(response.getCpfCnpj()).isEqualTo("26.343.835/0001-38");
    }

    @Test
    @DisplayName("Deve buscar serviços por ID quando usuário for funcionário")
    public void buscarServicoPorId_deveBuscarServico_quandoInformarIdSendoFuncionario() {
        var funcionario = umUsuarioAutenticadoFuncionario();
        funcionario.setId(10);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(funcionario);

        var response = service.buscarServicoPorId(1);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getDescricao()).isEqualTo("Corte exclusivo");
        assertThat(response.getPreco()).isEqualTo(15.90);
        assertThat(response.getEmpresa()).isEqualTo("Empresa 01 Edicao");
        assertThat(response.getCpfCnpj()).isEqualTo("26.343.835/0001-38");
    }

    @Test
    @DisplayName("Deve lançar exception quando tentar buscar por ID e não tiver permissão sendo sócio")
    public void buscarServicoPorId_deveLancarException_quandoSocioNaoPossuirPermissao() {
        var socio = umUsuarioAutenticadoSocio();
        socio.setId(6);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(socio);

        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.buscarServicoPorId(2))
            .withMessage("Usuário sem permissão para visualizar essa empresa.");
    }

    @Test
    @DisplayName("Deve lançar exception quando tentar buscar por ID e não tiver permissão sendo funcionário")
    public void buscarServicoPorId_deveLancarException_quandoFuncionarioNaoPossuirPermissao() {
        var proprietario = umUsuarioAutenticadoProprietario();
        proprietario.setId(2);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(proprietario);

        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.buscarServicoPorId(2))
            .withMessage("Usuário sem permissão para visualizar essa empresa.");
    }

    @Test
    @DisplayName("Deve lançar exception quando tentar buscar por ID e não encontrar")
    public void buscarServicoPorId_deveLancarException_quandoNaoEncontrarServico() {
        var proprietario = umUsuarioAutenticadoProprietario();
        proprietario.setId(2);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(proprietario);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.buscarServicoPorId(1000))
            .withMessage("O serviço não foi encontrado.");
    }

    @Test
    @DisplayName("Deve retornar model de Servico quando informar ID")
    public void buscarPorId_deveRetornarModelServico_quandoInformarId() {
        var servico = service.buscarPorId(1);

        assertThat(servico).isNotNull();
        assertThat(servico.getId()).isEqualTo(1);
        assertThat(servico.getDescricao()).isEqualTo("Corte exclusivo");
        assertThat(servico.getPreco()).isEqualTo(15.90);
        assertThat(servico.getEmpresa().getNome()).isEqualTo("Empresa 01 Edicao");
        assertThat(servico.getEmpresa().getCpfCnpj()).isEqualTo("26.343.835/0001-38");
    }

    @Test
    @DisplayName("Deve lançar exception quando tentar buscar por ID e não encontrar")
    public void buscarPorId_deveLancarException_quandoNaoEncontrarServico() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.buscarPorId(1000))
            .withMessage("O serviço não foi encontrado.");
    }

    @Test
    @DisplayName("Deve buscar serviços por ID quando usuário for admin")
    public void buscarServicosPorEmpresa_deveBuscarServicos_quandoInformarEmpresaIdSendoAdmin() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoAdmin());

        assertThat(service.buscarServicosPorEmpresa(4))
            .extracting("id", "descricao", "preco", "empresa", "cpfCnpj")
            .containsExactly(
                tuple(3, "Corte e lavagem", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38"),
                tuple(1, "Corte exclusivo", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38"),
                tuple(7, "Corte, barba e lavagem", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38"),
                tuple(4, "Lavagem", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38")
            );
    }

    @Test
    @DisplayName("Deve buscar serviços por ID quando usuário for proprietário")
    public void buscarServicosPorEmpresa_deveBuscarServicos_quandoInformarEmpresaIdSendoProprietario() {
        var proprietario = umUsuarioAutenticadoProprietario();
        proprietario.setId(2);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(proprietario);

        assertThat(service.buscarServicosPorEmpresa(4))
            .extracting("id", "descricao", "preco", "empresa", "cpfCnpj")
            .containsExactly(
                tuple(3, "Corte e lavagem", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38"),
                tuple(1, "Corte exclusivo", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38"),
                tuple(7, "Corte, barba e lavagem", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38"),
                tuple(4, "Lavagem", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38")
            );
    }

    @Test
    @DisplayName("Deve buscar serviços por ID quando usuário for sócio")
    public void buscarServicosPorEmpresa_deveBuscarServicos_quandoInformarEmpresaIdSendoSocio() {
        var socio = umUsuarioAutenticadoSocio();
        socio.setId(6);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(socio);

        assertThat(service.buscarServicosPorEmpresa(4))
            .extracting("id", "descricao", "preco", "empresa", "cpfCnpj")
            .containsExactly(
                tuple(3, "Corte e lavagem", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38"),
                tuple(1, "Corte exclusivo", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38"),
                tuple(7, "Corte, barba e lavagem", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38"),
                tuple(4, "Lavagem", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38")
            );
    }

    @Test
    @DisplayName("Deve buscar serviços por ID quando usuário for funcionário")
    public void buscarServicosPorEmpresa_deveBuscarServicos_quandoInformarEmpresaIdSendoFuncionario() {
        var funcionario = umUsuarioAutenticadoFuncionario();
        funcionario.setId(10);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(funcionario);

        assertThat(service.buscarServicosPorEmpresa(4))
            .extracting("id", "descricao", "preco", "empresa", "cpfCnpj")
            .containsExactly(
                tuple(3, "Corte e lavagem", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38"),
                tuple(1, "Corte exclusivo", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38"),
                tuple(7, "Corte, barba e lavagem", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38"),
                tuple(4, "Lavagem", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38")
            );
    }

    @Test
    @DisplayName("Deve buscar set de serviços quando solicitar por IDs")
    public void buscarServicosPorIds_deveRetornarSetComServicos_quandoSolicitadoPorIds() {
        assertThat(service.buscarServicosPorIds(List.of(1, 3, 4, 7)))
            .extracting("id", "descricao", "preco", "empresa.nome", "empresa.cpfCnpj")
            .containsExactlyInAnyOrder(
                tuple(3, "Corte e lavagem", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38"),
                tuple(1, "Corte exclusivo", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38"),
                tuple(7, "Corte, barba e lavagem", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38"),
                tuple(4, "Lavagem", 15.9, "Empresa 01 Edicao", "26.343.835/0001-38")
            );
    }
}