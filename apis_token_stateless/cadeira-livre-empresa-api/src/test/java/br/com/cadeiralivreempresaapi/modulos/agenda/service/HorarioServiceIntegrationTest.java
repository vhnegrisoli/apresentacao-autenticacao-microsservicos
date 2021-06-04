package br.com.cadeiralivreempresaapi.modulos.agenda.service;

import br.com.cadeiralivreempresaapi.modulos.agenda.repository.AgendaRepository;
import br.com.cadeiralivreempresaapi.modulos.agenda.repository.DiaDaSemanaRepository;
import br.com.cadeiralivreempresaapi.modulos.agenda.repository.HorarioRepository;
import br.com.cadeiralivreempresaapi.modulos.empresa.service.EmpresaService;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.UsuarioAcessoService;
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

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ActiveProfiles("test")
@Import(HorarioService.class)
@ExtendWith(MockitoExtension.class)
@Sql(scripts = {
    "classpath:/usuarios_tests.sql",
    "classpath:/funcionarios_tests.sql",
    "classpath:/agendas_tests.sql"
})
public class HorarioServiceIntegrationTest {

    @Autowired
    private HorarioService horarioService;
    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private DiaDaSemanaRepository diaDaSemanaRepository;
    @MockBean
    private EmpresaService empresaService;
    @MockBean
    private AgendaRepository agendaRepository;
    @MockBean
    private UsuarioAcessoService acessoService;

    @Test
    @DisplayName("Deve buscar horários ordenados quando informar empresa ID")
    public void buscarHorariosPorEmpresa_deveBuscarHorariosOrdenados_quandoInformarEmpresaId() {
        assertThat(horarioService.buscarHorariosPorEmpresa(7))
            .extracting("id", "horario", "diaDaSemanaId", "diaNumerico", "diaNome")
            .containsExactly(
                tuple(43, LocalTime.parse("05:30"), 27, 1, "Terça-feira"),
                tuple(42, LocalTime.parse("11:30"), 26, 0, "Segunda-feira"),
                tuple(41, LocalTime.parse("13:30"), 25, 6, "Domingo")
            );

        verify(acessoService, times(1)).validarPermissoesDoUsuario(anyInt());
    }

    @Test
    @DisplayName("Deve buscar dias da semana quando existir")
    public void buscarDiasDaSemana_deveBuscarDiasDaSemana_quandoExistir() {
        assertThat(horarioService.buscarDiasDaSemana())
            .extracting("id", "dia", "diaNome")
            .containsExactly(
                tuple(25, 6, "Domingo"),
                tuple(26, 0, "Segunda-feira"),
                tuple(27, 1, "Terça-feira"),
                tuple(28, 2, "Quarta-feira"),
                tuple(29, 3, "Quinta-feira"),
                tuple(30, 4, "Sexta-feira"),
                tuple(31, 5, "Sábado")
            );
    }
}