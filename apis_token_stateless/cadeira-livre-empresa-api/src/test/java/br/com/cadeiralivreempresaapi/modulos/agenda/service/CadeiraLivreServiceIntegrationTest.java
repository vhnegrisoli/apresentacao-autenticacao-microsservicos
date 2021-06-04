package br.com.cadeiralivreempresaapi.modulos.agenda.service;

import br.com.cadeiralivreempresaapi.config.exception.PermissaoException;
import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.agenda.repository.AgendaRepository;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Empresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.service.EmpresaService;
import br.com.cadeiralivreempresaapi.modulos.empresa.service.EnderecoService;
import br.com.cadeiralivreempresaapi.modulos.funcionario.service.FuncionarioService;
import br.com.cadeiralivreempresaapi.modulos.jwt.service.JwtService;
import br.com.cadeiralivreempresaapi.modulos.notificacao.service.NotificacaoService;
import br.com.cadeiralivreempresaapi.modulos.transacao.service.TransacaoService;
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

import static br.com.cadeiralivreempresaapi.modulos.agenda.mocks.AgendaMocks.*;
import static br.com.cadeiralivreempresaapi.modulos.comum.util.NumeroUtil.converterParaDuasCasasDecimais;
import static br.com.cadeiralivreempresaapi.modulos.jwt.mocks.JwtMocks.umJwtUsuarioResponse;
import static br.com.cadeiralivreempresaapi.modulos.jwt.util.JwtTestUtil.gerarTokenTeste;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
@Import({
    CadeiraLivreService.class,
    AgendaService.class,
    ServicoService.class,
    EmpresaService.class,
    FuncionarioService.class,
    JwtService.class,
    UsuarioService.class,
    PermissaoService.class
})
@ExtendWith(MockitoExtension.class)
@Sql(scripts = {
    "classpath:/usuarios_tests.sql",
    "classpath:/funcionarios_tests.sql",
    "classpath:/agendas_tests.sql"
})
public class CadeiraLivreServiceIntegrationTest {

    @Autowired
    private CadeiraLivreService service;
    @Autowired
    private AgendaService agendaService;
    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private ServicoService servicoService;
    @Autowired
    private EmpresaService empresaService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UsuarioAcessoService acessoService;
    @MockBean
    private AutenticacaoService autenticacaoService;
    @MockBean
    private NotificacaoService notificacaoService;
    @MockBean
    private PasswordEncoder encoder;
    @MockBean
    private HorarioService horarioService;
    @MockBean
    private EnderecoService enderecoService;
    @MockBean
    private TransacaoService transacaoService;

    @Test
    @DisplayName("Deve buscar cadeiras livres quando existirem sem filtrar por empresas")
    public void buscarCadeirasLivresDisponiveis_deveBuscarCadeirasLivres_quandoExistiremDisponiveis() {
        agendaRepository.deleteAll();

        when(jwtService.verificarUsuarioValidoComTokenValida(anyString())).thenReturn(true);

        var cadeiraLivre = umaAgendaCadeiraLivre();
        cadeiraLivre.setId(null);
        cadeiraLivre.setEmpresa(new Empresa(4));
        agendaRepository.save(cadeiraLivre);

        assertThat(service.buscarCadeirasLivresDisponiveis("jwtValida", null))
            .extracting("situacao", "minutosDisponiveis", "totalPagamento", "totalDesconto", "totalServico")
            .containsExactlyInAnyOrder(
                tuple("Disponível",
                    20,
                    converterParaDuasCasasDecimais(25.00),
                    converterParaDuasCasasDecimais(12.00),
                    converterParaDuasCasasDecimais(25.00))
            );

        verify(jwtService, times(1)).verificarUsuarioValidoComTokenValida(anyString());
    }

    @Test
    @DisplayName("Deve buscar cadeiras livres quando existirem filtrando por empresas")
    public void buscarCadeirasLivresDisponiveis_deveBuscarCadeirasLivres_quandoExistiremDisponiveisComFiltroPorEmpresa() {
        agendaRepository.deleteAll();

        when(jwtService.verificarUsuarioValidoComTokenValida(anyString())).thenReturn(true);

        var cadeiraLivre = umaAgendaCadeiraLivre();
        cadeiraLivre.setId(null);
        cadeiraLivre.setEmpresa(new Empresa(4));
        agendaRepository.save(cadeiraLivre);

        assertThat(service.buscarCadeirasLivresDisponiveis("jwtValida", 4))
            .extracting("situacao", "minutosDisponiveis", "totalPagamento", "totalDesconto", "totalServico")
            .containsExactlyInAnyOrder(
                tuple("Disponível",
                    20,
                    converterParaDuasCasasDecimais(25.00),
                    converterParaDuasCasasDecimais(12.00),
                    converterParaDuasCasasDecimais(25.00))
            );

        assertThat(service.buscarCadeirasLivresDisponiveis(gerarTokenTeste(), 7)).isEmpty();

        verify(jwtService, times(2)).verificarUsuarioValidoComTokenValida(anyString());
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar buscar cadeiras livres disponíveis e token JWT não estiver válido.")
    public void buscarCadeirasLivresDisponiveis_deveLancarException_quandoTokenJwtNaoEstiverValido() {
        agendaRepository.deleteAll();

        when(jwtService.verificarUsuarioValidoComTokenValida(anyString())).thenReturn(false);

        var cadeiraLivre = umaAgendaCadeiraLivre();
        cadeiraLivre.setId(null);
        cadeiraLivre.setEmpresa(new Empresa(4));
        agendaRepository.save(cadeiraLivre);

        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.buscarCadeirasLivresDisponiveis("jwtInvalida", null))
            .withMessage("O usuário não está autenticado.");

        verify(jwtService, times(1)).verificarUsuarioValidoComTokenValida(anyString());
    }

    @Test
    @DisplayName("Deve buscar cadeiras livres do cliente quando existirem cadastradas.")
    public void buscarCadeirasLivresDoCliente_deveRetornarCadeirasLivres_seExistiremParaCliente() {
        agendaRepository.deleteAll();

        when(jwtService.verificarUsuarioValidoComTokenValida(anyString())).thenReturn(true);
        when(jwtService.recuperarDadosDoUsuarioDoToken(anyString())).thenReturn(umJwtUsuarioResponse());

        var cadeiraLivre = umaAgendaCadeiraLivre();
        cadeiraLivre.setId(null);
        cadeiraLivre.setEmpresa(new Empresa(4));
        cadeiraLivre.reservarParaCliente(umJwtUsuarioResponse());
        agendaRepository.save(cadeiraLivre);

        assertThat(service.buscarCadeirasLivresDoCliente("jwtValida"))
            .extracting("situacao", "minutosDisponiveis", "totalPagamento", "totalDesconto", "totalServico",
                "cliente.id", "cliente.nome", "cliente.email", "cliente.cpf")
            .containsExactlyInAnyOrder(
                tuple("Reservada",
                    null,
                    converterParaDuasCasasDecimais(25.00),
                    converterParaDuasCasasDecimais(12.00),
                    converterParaDuasCasasDecimais(25.00),
                    "5cd48099-1009-43c4-b979-f68148a2a81d",
                    "Victor Hugo Negrisoli",
                    "vhnegrisoli@gmail.com",
                    "103.324.589-54")
            );

        verify(jwtService, times(1)).verificarUsuarioValidoComTokenValida(anyString());
        verify(jwtService, times(1)).recuperarDadosDoUsuarioDoToken(anyString());
    }

    @Test
    @DisplayName("Deve buscar cadeira livre por ID quando existir e quando cliente tiver permissão.")
    public void buscarCadeiraLivrePorId_deveRetornarCadeiraLivre_quandoExistirPorIdEClienteTiverPermissao() {
        agendaRepository.deleteAll();

        when(jwtService.verificarUsuarioValidoComTokenValida(anyString())).thenReturn(true);
        when(jwtService.recuperarDadosDoUsuarioDoToken(anyString())).thenReturn(umJwtUsuarioResponse());

        var cadeiraLivre = umaAgendaCadeiraLivre();
        cadeiraLivre.setId(null);
        cadeiraLivre.setEmpresa(new Empresa(4));
        cadeiraLivre.reservarParaCliente(umJwtUsuarioResponse());
        agendaRepository.save(cadeiraLivre);
        var idSalvo = agendaRepository.findAll().get(0).getId();
        var response = service.buscarCadeiraLivrePorId(idSalvo, "jwtValida");

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(idSalvo);
        assertThat(response.getSituacao()).isEqualTo("Reservada");
        assertThat(response.getTotalPagamento()).isEqualTo(converterParaDuasCasasDecimais(25.00));
        assertThat(response.getTotalDesconto()).isEqualTo(converterParaDuasCasasDecimais(12.00));
        assertThat(response.getTotalServico()).isEqualTo(converterParaDuasCasasDecimais(25.00));
        assertThat(response.getCliente().getId()).isEqualTo("5cd48099-1009-43c4-b979-f68148a2a81d");
        assertThat(response.getCliente().getNome()).isEqualTo("Victor Hugo Negrisoli");
        assertThat(response.getCliente().getEmail()).isEqualTo("vhnegrisoli@gmail.com");
        assertThat(response.getCliente().getCpf()).isEqualTo("103.324.589-54");

        verify(jwtService, times(1)).verificarUsuarioValidoComTokenValida(anyString());
        verify(jwtService, times(1)).recuperarDadosDoUsuarioDoToken(anyString());
    }

    @Test
    @DisplayName("Deve lançar exception quando cliente não tiver permissão para visualizar cadeira livre de outro cliente.")
    public void buscarCadeiraLivrePorId_deveLancarException_quandoClienteTentarVerCadeiraLivreDeOutroCliente() {
        agendaRepository.deleteAll();

        var outroCliente = umJwtUsuarioResponse();
        outroCliente.setId("123456789");

        when(jwtService.verificarUsuarioValidoComTokenValida(anyString())).thenReturn(true);
        when(jwtService.recuperarDadosDoUsuarioDoToken(anyString())).thenReturn(outroCliente);

        var cadeiraLivre = umaAgendaCadeiraLivre();
        cadeiraLivre.setId(null);
        cadeiraLivre.setEmpresa(new Empresa(4));
        cadeiraLivre.reservarParaCliente(umJwtUsuarioResponse());
        agendaRepository.save(cadeiraLivre);
        var idSalvo = agendaRepository.findAll().get(0).getId();

        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.buscarCadeiraLivrePorId(idSalvo, "jwtValida"))
            .withMessage("Você não possui permissão para visualizar essa cadeira livre.");

        verify(jwtService, times(1)).verificarUsuarioValidoComTokenValida(anyString());
        verify(jwtService, times(1)).recuperarDadosDoUsuarioDoToken(anyString());
    }

    @Test
    @DisplayName("Deve buscar cadeira livre por empresa ID quando existir.")
    public void buscarCadeirasLivresPorEmpresa_deveRetornarCadeirasLivres_quandoExistirPorEmpresa() {
        agendaRepository.deleteAll();

        when(jwtService.verificarUsuarioValidoComTokenValida(anyString())).thenReturn(true);
        when(jwtService.recuperarDadosDoUsuarioDoToken(anyString())).thenReturn(umJwtUsuarioResponse());

        var cadeiraLivre = umaAgendaCadeiraLivre();
        cadeiraLivre.setId(null);
        cadeiraLivre.setEmpresa(new Empresa(4));
        agendaRepository.save(cadeiraLivre);

        assertThat(service.buscarCadeirasLivresPorEmpresa(4))
            .extracting("situacao", "minutosDisponiveis", "totalPagamento", "totalDesconto", "totalServico", "cliente")
            .containsExactlyInAnyOrder(
                tuple("Disponível",
                    20,
                    converterParaDuasCasasDecimais(25.00),
                    converterParaDuasCasasDecimais(12.00),
                    converterParaDuasCasasDecimais(25.00),
                    null)
            );

        assertThat(service.buscarCadeirasLivresPorEmpresa(7)).isEmpty();

        verify(acessoService, times(2)).validarPermissoesDoUsuario(anyInt());
    }

    @Test
    @DisplayName("Deve buscar cadeira livre response quando existir por ID e por empresa ID.")
    public void buscarCadeiraLivreResponsePorIdEPorEmpresaId_deveRetornarCadeiraLivreResponse_quandoExistirPorIdEEmpresaId() {
        agendaRepository.deleteAll();

        when(jwtService.verificarUsuarioValidoComTokenValida(anyString())).thenReturn(true);
        when(jwtService.recuperarDadosDoUsuarioDoToken(anyString())).thenReturn(umJwtUsuarioResponse());

        var cadeiraLivre = umaAgendaCadeiraLivre();
        cadeiraLivre.setId(null);
        cadeiraLivre.setEmpresa(new Empresa(4));
        agendaRepository.save(cadeiraLivre);
        var idSalvo = agendaRepository.findAll().get(0).getId();

        assertThat(service.buscarCadeiraLivreResponsePorIdEPorEmpresaId(idSalvo, 4))
            .extracting("situacao", "minutosDisponiveis", "totalPagamento", "totalDesconto", "totalServico", "cliente")
            .containsExactlyInAnyOrder(
                "Disponível",
                20,
                converterParaDuasCasasDecimais(25.00),
                converterParaDuasCasasDecimais(12.00),
                converterParaDuasCasasDecimais(25.00),
                null
            );

        verify(acessoService, times(1)).validarPermissoesDoUsuario(anyInt());
    }

    @Test
    @DisplayName("Deve lançar exception quando buscar por ID e empresa ID e não encontrar cadeira livre.")
    public void buscarCadeiraLivreResponsePorIdEPorEmpresaId_deveLancarException_quandoNaoEncontrarPorIdEPorEmpresaId() {
        agendaRepository.deleteAll();

        when(jwtService.verificarUsuarioValidoComTokenValida(anyString())).thenReturn(true);
        when(jwtService.recuperarDadosDoUsuarioDoToken(anyString())).thenReturn(umJwtUsuarioResponse());

        var cadeiraLivre = umaAgendaCadeiraLivre();
        cadeiraLivre.setId(null);
        cadeiraLivre.setEmpresa(new Empresa(4));
        agendaRepository.save(cadeiraLivre);
        var idSalvo = agendaRepository.findAll().get(0).getId();

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.buscarCadeiraLivreResponsePorIdEPorEmpresaId(idSalvo, 7))
            .withMessage("A cadeira livre não foi encontrada.");

        verify(acessoService, times(1)).validarPermissoesDoUsuario(anyInt());
    }

    @Test
    @DisplayName("Deve reservar cadeira livre quando informar dados do cliente.")
    public void reservarCadeiraLivre_deveReservarCadeiraLivre_quandoInformarDadosCliente() {
        agendaRepository.deleteAll();

        when(jwtService.verificarUsuarioValidoComTokenValida(anyString())).thenReturn(true);
        when(jwtService.recuperarDadosDoUsuarioDoToken(anyString())).thenReturn(umJwtUsuarioResponse());
        when(transacaoService.realizarTransacao(any(), any(), any(), any()))
            .thenReturn(umaTransacaoResponse());

        var cadeiraLivre = umaAgendaCadeiraLivre();
        cadeiraLivre.setId(null);
        cadeiraLivre.setEmpresa(new Empresa(4));
        agendaRepository.save(cadeiraLivre);
        var idSalvo = agendaRepository.findAll().get(0).getId();

        var cadeiraLivreSalva = agendaRepository.findById(idSalvo).get();

        assertThat(cadeiraLivreSalva).isNotNull();
        assertThat(cadeiraLivreSalva.isValida()).isTrue();
        assertThat(cadeiraLivreSalva.isDisponivel()).isTrue();
        assertThat(cadeiraLivreSalva.getClienteId()).isNull();
        assertThat(cadeiraLivreSalva.getClienteNome()).isNull();
        assertThat(cadeiraLivreSalva.getClienteEmail()).isNull();
        assertThat(cadeiraLivreSalva.getClienteCpf()).isNull();

        var reserva = umaCadeiraLivreReservaRequest();
        reserva.setCadeiraLivreId(idSalvo);

        var cadeiraLivreReservada = service.reservarCadeiraLivre(reserva);

        assertThat(cadeiraLivreReservada).isNotNull();
        assertThat(cadeiraLivreReservada.getSituacao()).isEqualTo("Reservada");
        assertThat(cadeiraLivreReservada.getHorarioExpiracao()).isNull();
        assertThat(cadeiraLivreReservada.getCliente()).isNotNull();
        assertThat(cadeiraLivreReservada.getCliente().getId()).isNotNull();
        assertThat(cadeiraLivreReservada.getCliente().getId()).isEqualTo("5cd48099-1009-43c4-b979-f68148a2a81d");
        assertThat(cadeiraLivreReservada.getCliente().getNome()).isEqualTo("Victor Hugo Negrisoli");
        assertThat(cadeiraLivreReservada.getCliente().getEmail()).isEqualTo("vhnegrisoli@gmail.com");
        assertThat(cadeiraLivreReservada.getCliente().getCpf()).isEqualTo("103.324.589-54");

        verify(jwtService, times(1)).verificarUsuarioValidoComTokenValida(anyString());
        verify(jwtService, times(1)).recuperarDadosDoUsuarioDoToken(anyString());
    }

    @Test
    @DisplayName("Deve reservar cadeira livre quando informar dados do cliente.")
    public void reservarCadeiraLivreParaCliente_deveReservarCadeiraLivreViaRequest_quandoInformarDadosCliente() {
        agendaRepository.deleteAll();

        when(jwtService.verificarUsuarioValidoComTokenValida(anyString())).thenReturn(true);
        when(jwtService.recuperarDadosDoUsuarioDoToken(anyString())).thenReturn(umJwtUsuarioResponse());
        when(transacaoService.realizarTransacao(any(), anyString(), any(), anyString()))
            .thenReturn(umaTransacaoResponse());

        var cadeiraLivre = umaAgendaCadeiraLivre();
        cadeiraLivre.setId(null);
        cadeiraLivre.setEmpresa(new Empresa(4));
        agendaRepository.save(cadeiraLivre);
        var idSalvo = agendaRepository.findAll().get(0).getId();

        var cadeiraLivreSalva = agendaRepository.findById(idSalvo).get();

        assertThat(cadeiraLivreSalva).isNotNull();
        assertThat(cadeiraLivreSalva.isValida()).isTrue();
        assertThat(cadeiraLivreSalva.isDisponivel()).isTrue();
        assertThat(cadeiraLivreSalva.getClienteId()).isNull();
        assertThat(cadeiraLivreSalva.getClienteNome()).isNull();
        assertThat(cadeiraLivreSalva.getClienteEmail()).isNull();
        assertThat(cadeiraLivreSalva.getClienteCpf()).isNull();

        var reserva = umaCadeiraLivreReservaRequest();
        reserva.setCadeiraLivreId(idSalvo);

        var cadeiraLivreReservada = service.reservarCadeiraLivreParaCliente(idSalvo, "token", "cartaoId");

        assertThat(cadeiraLivreReservada).isNotNull();
        assertThat(cadeiraLivreReservada.getSituacao()).isEqualTo("Reservada");
        assertThat(cadeiraLivreReservada.getHorarioExpiracao()).isNull();
        assertThat(cadeiraLivreReservada.getCliente()).isNotNull();
        assertThat(cadeiraLivreReservada.getCliente().getId()).isNotNull();
        assertThat(cadeiraLivreReservada.getCliente().getId()).isEqualTo("5cd48099-1009-43c4-b979-f68148a2a81d");
        assertThat(cadeiraLivreReservada.getCliente().getNome()).isEqualTo("Victor Hugo Negrisoli");
        assertThat(cadeiraLivreReservada.getCliente().getEmail()).isEqualTo("vhnegrisoli@gmail.com");
        assertThat(cadeiraLivreReservada.getCliente().getCpf()).isEqualTo("103.324.589-54");

        verify(jwtService, times(1)).verificarUsuarioValidoComTokenValida(anyString());
        verify(jwtService, times(1)).recuperarDadosDoUsuarioDoToken(anyString());
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar reservar cadeira livre que não está mais disponível.")
    public void reservarCadeiraLivre_deveLancarException_quandoCadeiraLivreNaoEstiverMaisDisponivel() {
        agendaRepository.deleteAll();

        when(jwtService.verificarUsuarioValidoComTokenValida(anyString())).thenReturn(true);
        when(jwtService.recuperarDadosDoUsuarioDoToken(anyString())).thenReturn(umJwtUsuarioResponse());

        var cadeiraLivre = umaAgendaCadeiraLivre();
        cadeiraLivre.setId(null);
        cadeiraLivre.setEmpresa(new Empresa(4));
        cadeiraLivre.reservarParaCliente(umJwtUsuarioResponse());
        agendaRepository.save(cadeiraLivre);
        var idSalvo = agendaRepository.findAll().get(0).getId();

        var reserva = umaCadeiraLivreReservaRequest();
        reserva.setCadeiraLivreId(idSalvo);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.reservarCadeiraLivre(reserva))
            .withMessage("Desculpe, mas esta cadeira livre não está mais disponível.");

        verify(jwtService, times(1)).verificarUsuarioValidoComTokenValida(anyString());
        verify(jwtService, times(1)).recuperarDadosDoUsuarioDoToken(anyString());
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar reservar cadeira livre com dados insuficientes do cliente.")
    public void reservarCadeiraLivre_deveLancarException_quandoDadosDoClienteEstiveremIncompletos() {
        agendaRepository.deleteAll();

        when(jwtService.verificarUsuarioValidoComTokenValida(anyString())).thenReturn(true);
        var clienteDadosFaltando = umJwtUsuarioResponse();
        clienteDadosFaltando.setNome(null);
        when(jwtService.recuperarDadosDoUsuarioDoToken(anyString())).thenReturn(clienteDadosFaltando);

        var cadeiraLivre = umaAgendaCadeiraLivre();
        cadeiraLivre.setId(null);
        cadeiraLivre.setEmpresa(new Empresa(4));
        agendaRepository.save(cadeiraLivre);
        var idSalvo = agendaRepository.findAll().get(0).getId();

        var reserva = umaCadeiraLivreReservaRequest();
        reserva.setCadeiraLivreId(idSalvo);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.reservarCadeiraLivre(reserva))
            .withMessage("Para registrar uma cadeira livre são necessárias todas "
                + "as seguintes informações: id, nome, CPF e e-mail.");

        verify(jwtService, times(1)).verificarUsuarioValidoComTokenValida(anyString());
        verify(jwtService, times(1)).recuperarDadosDoUsuarioDoToken(anyString());
    }
}