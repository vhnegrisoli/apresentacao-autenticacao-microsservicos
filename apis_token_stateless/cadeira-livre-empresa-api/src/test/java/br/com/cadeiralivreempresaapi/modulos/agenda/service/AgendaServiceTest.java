package br.com.cadeiralivreempresaapi.modulos.agenda.service;

import br.com.cadeiralivreempresaapi.modulos.agenda.repository.AgendaRepository;
import br.com.cadeiralivreempresaapi.modulos.empresa.service.EmpresaService;
import br.com.cadeiralivreempresaapi.modulos.funcionario.service.FuncionarioService;
import br.com.cadeiralivreempresaapi.modulos.notificacao.service.NotificacaoService;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.AutenticacaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@ActiveProfiles("test")
@Import(AgendaService.class)
@ExtendWith(MockitoExtension.class)
@Sql(scripts = {
    "classpath:/usuarios_tests.sql",
    "classpath:/funcionarios_tests.sql",
    "classpath:/agendas_tests.sql"
})
public class AgendaServiceTest {

    @Autowired
    private AgendaService service;
    @Autowired
    private AgendaRepository agendaRepository;
    @MockBean
    private AutenticacaoService autenticacaoService;
    @MockBean
    private NotificacaoService notificacaoService;
    @MockBean
    private FuncionarioService funcionarioService;
    @MockBean
    private ServicoService servicoService;
    @MockBean
    private EmpresaService empresaService;
    @MockBean
    private HorarioService horarioService;

    @Test
    public void buscarAgendaPorId_deveBuscarAgenda_quandoInformarPorId() {
        var agenda = service.buscarAgendaPorId(1);
    }
}