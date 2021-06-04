package br.com.cadeiralivreempresaapi.modulos.agenda.service;

import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.agenda.enums.EDiaDaSemana;
import br.com.cadeiralivreempresaapi.modulos.agenda.repository.AgendaRepository;
import br.com.cadeiralivreempresaapi.modulos.agenda.repository.DiaDaSemanaRepository;
import br.com.cadeiralivreempresaapi.modulos.agenda.repository.HorarioRepository;
import br.com.cadeiralivreempresaapi.modulos.empresa.service.EmpresaService;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.UsuarioAcessoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static br.com.cadeiralivreempresaapi.modulos.agenda.mocks.HorarioMocks.*;
import static br.com.cadeiralivreempresaapi.modulos.empresa.mocks.EmpresaMocks.umaEmpresa;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HorarioServiceTest {

    @InjectMocks
    private HorarioService horarioService;
    @Mock
    private HorarioRepository horarioRepository;
    @Mock
    private DiaDaSemanaRepository diaDaSemanaRepository;
    @Mock
    private EmpresaService empresaService;
    @Mock
    private AgendaRepository agendaRepository;
    @Mock
    private UsuarioAcessoService acessoService;

    @Test
    @DisplayName("Deve salvar horário quando informar dados corretamente")
    public void salvarHorario_deveSalvarComSucesso_quandoDadosEstiveremCorretos() {
        when(empresaService.buscarPorId(anyInt())).thenReturn(umaEmpresa());
        when(diaDaSemanaRepository.findById(anyInt())).thenReturn(Optional.of(umDiaDaSemana()));
        when(horarioRepository.existsByHorarioAndEmpresaIdAndDiaDaSemanaId(any(), anyInt(), anyInt()))
            .thenReturn(false);
        when(horarioRepository.save(any())).thenReturn(umHorario());

        var horario = horarioService.salvarHorario(umHorarioRequest());

        assertThat(horario).isNotNull();
        assertThat(horario.getId()).isEqualTo(1);
        assertThat(horario.getHorario()).isEqualTo(LocalTime.of(12, 0));
        assertThat(horario.getDiaDaSemanaId()).isEqualTo(1);
        assertThat(horario.getDiaNumerico()).isEqualTo(0);
        assertThat(horario.getDiaNome()).isEqualTo("Segunda-feira");

        verify(empresaService, times(1)).buscarPorId(anyInt());
        verify(diaDaSemanaRepository, times(1)).findById(anyInt());
        verify(horarioRepository, times(1))
            .existsByHorarioAndEmpresaIdAndDiaDaSemanaId(any(), anyInt(), anyInt());
        verify(horarioRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exception quando não informar empresa no Request")
    public void salvarHorario_deveLancarException_quandoNaoInformarEmpresa() {
        var request = umHorarioRequest();
        request.setEmpresaId(null);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> horarioService.salvarHorario(request))
            .withMessage("A empresa deve ser informada.");

        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(diaDaSemanaRepository, times(0)).findById(anyInt());
        verify(horarioRepository, times(0))
            .existsByHorarioAndEmpresaIdAndDiaDaSemanaId(any(), anyInt(), anyInt());
        verify(horarioRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exception quando não informar horário no Request")
    public void salvarHorario_deveLancarException_quandoNaoInformarHorario() {
        var request = umHorarioRequest();
        request.setHorario(null);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> horarioService.salvarHorario(request))
            .withMessage("O horário deve ser informado.");

        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(diaDaSemanaRepository, times(0)).findById(anyInt());
        verify(horarioRepository, times(0))
            .existsByHorarioAndEmpresaIdAndDiaDaSemanaId(any(), anyInt(), anyInt());
        verify(horarioRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exception quando não informar dia da semana no Request")
    public void salvarHorario_deveLancarException_quandoNaoInformarDiaDaSemana() {
        var request = umHorarioRequest();
        request.setDiaSemanaId(null);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> horarioService.salvarHorario(request))
            .withMessage("O dia da semana deve ser informado.");

        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(diaDaSemanaRepository, times(0)).findById(anyInt());
        verify(horarioRepository, times(0))
            .existsByHorarioAndEmpresaIdAndDiaDaSemanaId(any(), anyInt(), anyInt());
        verify(horarioRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exception quando horário já existir para empresa no mesmo dia")
    public void salvarHorario_deveLancarException_quandoJaExistirHorarioParaEmpresaNoMesmoDia() {
        when(empresaService.buscarPorId(anyInt())).thenReturn(umaEmpresa());
        when(diaDaSemanaRepository.findById(anyInt())).thenReturn(Optional.of(umDiaDaSemana()));
        when(horarioRepository.existsByHorarioAndEmpresaIdAndDiaDaSemanaId(any(), anyInt(), anyInt()))
            .thenReturn(true);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> horarioService.salvarHorario(umHorarioRequest()))
            .withMessage("Este horário já está registrado para esta empresa neste dia.");

        verify(empresaService, times(1)).buscarPorId(anyInt());
        verify(diaDaSemanaRepository, times(1)).findById(anyInt());
        verify(horarioRepository, times(1))
            .existsByHorarioAndEmpresaIdAndDiaDaSemanaId(any(), anyInt(), anyInt());
        verify(horarioRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Deve alterar o horário quando informar dados corretamente")
    public void alterarHorario_deveSalvarComSucesso_quandoDadosEstiveremCorretos() {
        when(empresaService.buscarPorId(anyInt())).thenReturn(umaEmpresa());
        when(diaDaSemanaRepository.findById(anyInt())).thenReturn(Optional.of(umDiaDaSemana()));
        when(horarioRepository.existsByHorarioAndEmpresaIdAndDiaDaSemanaId(any(), anyInt(), anyInt()))
            .thenReturn(false);
        when(horarioRepository.save(any())).thenReturn(umHorario());

        var horario = horarioService.alterarHorario(umHorarioRequest(), 1);

        assertThat(horario).isNotNull();
        assertThat(horario.getId()).isEqualTo(1);
        assertThat(horario.getHorario()).isEqualTo(LocalTime.of(12, 0));
        assertThat(horario.getDiaDaSemanaId()).isEqualTo(1);
        assertThat(horario.getDiaNumerico()).isEqualTo(0);
        assertThat(horario.getDiaNome()).isEqualTo("Segunda-feira");

        verify(empresaService, times(1)).buscarPorId(anyInt());
        verify(diaDaSemanaRepository, times(1)).findById(anyInt());
        verify(horarioRepository, times(1))
            .existsByHorarioAndEmpresaIdAndDiaDaSemanaId(any(), anyInt(), anyInt());
        verify(horarioRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exception quando não informar empresa no Request")
    public void alterarHorario_deveLancarException_quandoNaoInformarEmpresa() {
        var request = umHorarioRequest();
        request.setEmpresaId(null);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> horarioService.alterarHorario(request, 1))
            .withMessage("A empresa deve ser informada.");

        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(diaDaSemanaRepository, times(0)).findById(anyInt());
        verify(horarioRepository, times(0))
            .existsByHorarioAndEmpresaIdAndDiaDaSemanaId(any(), anyInt(), anyInt());
        verify(horarioRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exception quando não informar horário no Request")
    public void alterarHorario_deveLancarException_quandoNaoInformarHorario() {
        var request = umHorarioRequest();
        request.setHorario(null);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> horarioService.alterarHorario(request, 1))
            .withMessage("O horário deve ser informado.");

        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(diaDaSemanaRepository, times(0)).findById(anyInt());
        verify(horarioRepository, times(0))
            .existsByHorarioAndEmpresaIdAndDiaDaSemanaId(any(), anyInt(), anyInt());
        verify(horarioRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exception quando não informar dia da semana no Request")
    public void alterarHorario_deveLancarException_quandoNaoInformarDiaDaSemana() {
        var request = umHorarioRequest();
        request.setDiaSemanaId(null);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> horarioService.alterarHorario(request, 1))
            .withMessage("O dia da semana deve ser informado.");

        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(diaDaSemanaRepository, times(0)).findById(anyInt());
        verify(horarioRepository, times(0))
            .existsByHorarioAndEmpresaIdAndDiaDaSemanaId(any(), anyInt(), anyInt());
        verify(horarioRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exception quando horário já existir para empresa no mesmo dia")
    public void alterarHorario_deveLancarException_quandoJaExistirHorarioParaEmpresaNoMesmoDia() {
        when(empresaService.buscarPorId(anyInt())).thenReturn(umaEmpresa());
        when(diaDaSemanaRepository.findById(anyInt())).thenReturn(Optional.of(umDiaDaSemana()));
        when(horarioRepository.existsByHorarioAndEmpresaIdAndDiaDaSemanaId(any(), anyInt(), anyInt()))
            .thenReturn(true);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> horarioService.alterarHorario(umHorarioRequest(), 1))
            .withMessage("Este horário já está registrado para esta empresa neste dia.");

        verify(empresaService, times(1)).buscarPorId(anyInt());
        verify(diaDaSemanaRepository, times(1)).findById(anyInt());
        verify(horarioRepository, times(1))
            .existsByHorarioAndEmpresaIdAndDiaDaSemanaId(any(), anyInt(), anyInt());
        verify(horarioRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Deve remover horário caso não exista agenda marcada")
    public void removerHorarioDoDia_deveRemoverHorario_quandoNaoExistirHorarioMarcado() {
        when(horarioRepository.findById(anyInt())).thenReturn(Optional.of(umHorario()));
        when(agendaRepository.existsByHorarioId(anyInt())).thenReturn(false);

        var response = horarioService.removerHorarioDoDia(1);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Horário removido com sucesso!");

        verify(horarioRepository, times(1)).findById(anyInt());
        verify(acessoService, times(1)).validarPermissoesDoUsuario(anyInt());
        verify(horarioRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Deve lançar exception quando tentar remover horário e não encontrar")
    public void removerHorarioDoDia_deveLancarException_quandoNaoEncontrarHorario() {
        when(horarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> horarioService.removerHorarioDoDia(1))
            .withMessage("O horário não foi encontrado.");

        verify(horarioRepository, times(1)).findById(anyInt());
        verify(acessoService, times(0)).validarPermissoesDoUsuario(anyInt());
        verify(horarioRepository, times(0)).delete(any());
    }

    @Test
    @DisplayName("Deve lançar exception quando tentar remover horário e já existirem agendas vinculadas")
    public void removerHorarioDoDia_deveLancarException_quandoJaExistiremAgendasParaOHorario() {
        when(horarioRepository.findById(anyInt())).thenReturn(Optional.of(umHorario()));
        when(agendaRepository.existsByHorarioId(anyInt())).thenReturn(true);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> horarioService.removerHorarioDoDia(1))
            .withMessage("Já existe um agendamento para este horário.");

        verify(horarioRepository, times(1)).findById(anyInt());
        verify(acessoService, times(1)).validarPermissoesDoUsuario(anyInt());
        verify(horarioRepository, times(0)).delete(any());
    }

    @Test
    @DisplayName("Deve retornar horário quando encontrar por ID")
    public void buscarPorId_deveRetornarHorarioPorId_quandoEncontrar() {
        when(horarioRepository.findById(anyInt())).thenReturn(Optional.of(umHorario()));

        var horario = horarioService.buscarPorId(1);

        assertThat(horario).isNotNull();
        assertThat(horario.getId()).isEqualTo(1);
        assertThat(horario.getHorario()).isEqualTo(LocalTime.of(12, 0));
        assertThat(horario.getDiaDaSemana().getId()).isEqualTo(1);
        assertThat(horario.getDiaDaSemana().getDia()).isEqualTo(0);
        assertThat(horario.getDiaDaSemana().getDiaCodigo()).isEqualTo(EDiaDaSemana.SEGUNDA_FEIRA);
        assertThat(horario.getDiaDaSemana().getDiaNome()).isEqualTo("Segunda-feira");
        assertThat(horario.getEmpresa().getId()).isEqualTo(1);
        assertThat(horario.getEmpresa().getNome()).isEqualTo("Empresa 01");
        assertThat(horario.getEmpresa().getCpfCnpj()).isEqualTo("82.765.926/0001-32");

        verify(acessoService, times(1)).validarPermissoesDoUsuario(anyInt());
        verify(horarioRepository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Deve lançar exception quando não encontrar horário por ID")
    public void buscarPorId_deveLancarException_quandoNaoEncontrarPorId() {
        when(horarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> horarioService.buscarPorId(1))
            .withMessage("O horário não foi encontrado.");

        verify(acessoService, times(0)).validarPermissoesDoUsuario(anyInt());
        verify(horarioRepository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Deve buscar dia da semana quando encontrar por ID")
    public void buscarDiaDaSemanaPorId_deveBuscarDiaDaSemana_quandoInformarPorId() {
        when(diaDaSemanaRepository.findById(anyInt())).thenReturn(Optional.of(umDiaDaSemana()));

        var diaDaSemana = horarioService.buscarDiaDaSemanaPorId(anyInt());

        assertThat(diaDaSemana).isNotNull();
        assertThat(diaDaSemana.getId()).isEqualTo(1);
        assertThat(diaDaSemana.getDia()).isEqualTo(0);
        assertThat(diaDaSemana.getDiaCodigo()).isEqualTo(EDiaDaSemana.SEGUNDA_FEIRA);
        assertThat(diaDaSemana.getDiaNome()).isEqualTo("Segunda-feira");

        verify(diaDaSemanaRepository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Deve lançar exception quando não encontrar dia da semana por ID")
    public void buscarDiaDaSemanaPorId_deveLancarException_quandoNaoEncontrarPorId() {
        when(diaDaSemanaRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> horarioService.buscarDiaDaSemanaPorId(anyInt()))
            .withMessage("Este dia da semana não existe.");

        verify(diaDaSemanaRepository, times(1)).findById(anyInt());
    }
}