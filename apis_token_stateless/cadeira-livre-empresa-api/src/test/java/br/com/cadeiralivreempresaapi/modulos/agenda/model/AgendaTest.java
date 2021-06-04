package br.com.cadeiralivreempresaapi.modulos.agenda.model;

import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.agenda.enums.ESituacaoAgenda;
import br.com.cadeiralivreempresaapi.modulos.agenda.enums.ETipoAgenda;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import static br.com.cadeiralivreempresaapi.modulos.agenda.mocks.AgendaMocks.*;
import static br.com.cadeiralivreempresaapi.modulos.agenda.mocks.HorarioMocks.umHorario;
import static br.com.cadeiralivreempresaapi.modulos.agenda.mocks.ServicoMocks.umServico;
import static br.com.cadeiralivreempresaapi.modulos.jwt.mocks.JwtMocks.umJwtUsuarioResponse;
import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.umUsuarioAutenticadoAdmin;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class AgendaTest {

    @Test
    @DisplayName("Deve converter para Model de Agenda quando informar DTO de AgendaRequest")
    public void of_deveConverterParaModelDeAgenda_quandoInformarDtoDeAgendaRequest() {
        var agenda = Agenda.of(umaAgendaRequest());
        assertThat(agenda).isNotNull();
        assertThat(agenda.getEmpresa().getId()).isEqualTo(1);
        assertThat(agenda.getHorario()).isNull();
        assertThat(agenda.getServicos().iterator().next().getId()).isEqualTo(1);
        assertThat(agenda.getSituacao()).isEqualTo(ESituacaoAgenda.RESERVA);
        assertThat(agenda.getTipoAgenda()).isEqualTo(ETipoAgenda.HORARIO_MARCADO);
        assertThat(agenda.getTotalDesconto()).isNull();
    }

    @Test
    @DisplayName("Deve converter para Model de Agenda quando informar DTO de CadeiraLivreRequest")
    public void of_deveConverterParaModelDeAgenda_quandoInformarDtoDeCadeiraLivreRequest() {
        var agenda = Agenda.of(umaCadeiraLivreRequest(), umUsuarioAutenticadoAdmin(), Set.of(umServico()));
        assertThat(agenda).isNotNull();
        assertThat(agenda.getEmpresa().getId()).isEqualTo(1);
        assertThat(agenda.getHorario()).isNull();
        assertThat(agenda.getServicos().iterator().next().getId()).isEqualTo(1);
        assertThat(agenda.getSituacao()).isEqualTo(ESituacaoAgenda.DISPONIVEL);
        assertThat(agenda.getTipoAgenda()).isEqualTo(ETipoAgenda.CADEIRA_LIVRE);
        assertThat(agenda.getClienteId()).isNull();
        assertThat(agenda.getClienteNome()).isNull();
        assertThat(agenda.getClienteEmail()).isNull();
        assertThat(agenda.getClienteCpf()).isNull();
        assertThat(agenda.getTotalDesconto()).isEqualTo(7.500000298023224);
        assertThat(agenda.getTotalServico()).isEqualTo(25.0);
    }

    @Test
    @DisplayName("Deve calcular o total da agenda sem desconto quando não informar um desconto")
    public void calcularTotal_deveCalcularTotalSemDesconto_quandoNaoInfomrarDesconto() {
        var agenda = umaAgendaHorarioMarcado();
        assertThat(agenda.getTotalDesconto()).isNull();
        var servicoOutroPreco = umServico();
        servicoOutroPreco.setPreco(23.45);
        agenda.setServicos(Set.of(umServico(), servicoOutroPreco));
        agenda.calcularTotal(null);
        assertThat(agenda.getTotalDesconto()).isNotNull();
        assertThat(agenda.getTotalDesconto()).isEqualTo(0.0);
        assertThat(agenda.getTotalServico()).isEqualTo(48.45);
    }

    @Test
    @DisplayName("Deve calcular o total da agenda com desconto quando informar um desconto")
    public void calcularTotal_deveCalcularTotalComDesconto_quandoInfomrarDesconto() {
        var agenda = umaAgendaHorarioMarcado();
        assertThat(agenda.getTotalDesconto()).isNull();
        var servicoOutroPreco = umServico();
        servicoOutroPreco.setPreco(23.45);
        agenda.setServicos(Set.of(umServico(), servicoOutroPreco));
        agenda.calcularTotal(57.45f);
        assertThat(agenda.getTotalDesconto()).isNotNull();
        assertThat(agenda.getTotalDesconto()).isEqualTo(27.83452617824078);
    }

    @Test
    @DisplayName("Deve retornar True quando a agenda estiver na situação disponível")
    public void isDisponivel_deveRetornarTrue_quandoAgendaEstiverComSituacaoDisponivel() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.setSituacao(ESituacaoAgenda.DISPONIVEL);

        assertThat(agenda.isDisponivel()).isTrue();
        assertThat(agenda.isCancelada()).isFalse();
        assertThat(agenda.isReservada()).isFalse();
    }

    @Test
    @DisplayName("Deve retornar True quando a agenda estiver na situação cancelada")
    public void isCancelada_deveRetornarTrue_quandoAgendaEstiverComSituacaoCancelada() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.setSituacao(ESituacaoAgenda.CANCELADA);

        assertThat(agenda.isCancelada()).isTrue();
        assertThat(agenda.isDisponivel()).isFalse();
        assertThat(agenda.isReservada()).isFalse();
    }

    @Test
    @DisplayName("Deve retornar True quando a agenda estiver na situação reserva")
    public void isReservada_deveRetornarTrue_quandoAgendaEstiverComSituacaoReserva() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.setSituacao(ESituacaoAgenda.RESERVA);

        assertThat(agenda.isReservada()).isTrue();
        assertThat(agenda.isCancelada()).isFalse();
        assertThat(agenda.isDisponivel()).isFalse();
    }

    @Test
    @DisplayName("Deve retornar o horário da expiração da agenda")
    public void informarHorarioExpiracao_deveRetornarHorario_quandoSolicitado() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.setSituacao(ESituacaoAgenda.DISPONIVEL);
        agenda.setDataCadastro(LocalDateTime.parse("2020-01-01T00:00:00"));
        assertThat(agenda.informarHorarioExpiracao()).isEqualTo(LocalTime.parse("00:20:00"));
    }

    @Test
    @DisplayName("Deve retornar o tempo restante da expiração da agenda")
    public void informarTempoRestante_deveRetornarTempoRestante_quandoSolicitado() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.setSituacao(ESituacaoAgenda.DISPONIVEL);
        agenda.setDataCadastro(LocalDateTime.now().minusMinutes(5));
        assertThat(agenda.informarTempoRestante()).isGreaterThan(10L);
    }

    @Test
    @DisplayName("Deve retornar True quando estiver dentro do horário de expiração e com situação disponível")
    public void isValida_deveRetornarTrue_quandoEstiverDentroDoHorarioDeExpiracaoEDisponivel() {
        var agenda = umaAgendaCadeiraLivre();
        assertThat(agenda.isValida()).isTrue();
    }

    @Test
    @DisplayName("Deve retornar False quando não estiver dentro do horário de expiração")
    public void isValida_deveRetornarFalse_quandoNaoEstiverDentroDoHorarioDeExpiracao() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.setDataCadastro(LocalDateTime.now().minusMinutes(31));
        assertThat(agenda.isValida()).isFalse();
    }

    @Test
    @DisplayName("Deve retornar False quando não estiver com a situação disponível")
    public void isValida_deveRetornarFalse_quandoNaoEstiverComSituacaoDisponivel() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.definirSituacaoComoCancelada();
        assertThat(agenda.isValida()).isFalse();
    }

    @Test
    @DisplayName("Deve retornar False quando possuir cliente vinculado")
    public void isValida_deveRetornarFalse_quandoPossuirClienteVinculado() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.setClienteId("asdasd515s1a51d1a5");
        agenda.setClienteNome("testedadosfaltando@gmail.com");
        agenda.setClienteEmail("testedadoscliente@gmail.com");
        agenda.setClienteCpf("10332458954");
        assertThat(agenda.isValida()).isFalse();
    }

    @Test
    @DisplayName("Deve retornar False quando estiver dentro do horário de expiração e com situação disponível")
    public void isInvalida_deveRetornarFalse_quandoEstiverDentroDoHorarioDeExpiracaoEDisponivel() {
        var agenda = umaAgendaCadeiraLivre();
        assertThat(agenda.isInvalida()).isFalse();
    }

    @Test
    @DisplayName("Deve retornar True quando não estiver dentro do horário de expiração")
    public void isInvalida_deveRetornarTrue_quandoNaoEstiverDentroDoHorarioDeExpiracao() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.setDataCadastro(LocalDateTime.now().minusMinutes(31));
        assertThat(agenda.isInvalida()).isTrue();
    }

    @Test
    @DisplayName("Deve retornar True quando não estiver com a situação disponível")
    public void isInvalida_deveRetornarTrue_quandoNaoEstiverComSituacaoDisponivel() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.definirSituacaoComoCancelada();
        assertThat(agenda.isInvalida()).isTrue();
    }

    @Test
    @DisplayName("Deve retornar True quando possuir cliente vinculado")
    public void isInvalida_deveRetornarTrue_quandoPossuirClienteVinculado() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.setClienteId("asdasd515s1a51d1a5");
        agenda.setClienteNome("testedadosfaltando@gmail.com");
        agenda.setClienteEmail("testedadoscliente@gmail.com");
        agenda.setClienteCpf("10332458954");
        assertThat(agenda.isInvalida()).isTrue();
    }

    @Test
    @DisplayName("Deve retornar True quando não possuir qualquer dado de cliente vinculado")
    public void isCadeiraLivreSemClienteVinculado_deveRetornarTrue_quandoNaoPossuirCliente() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.definirSituacaoComoCancelada();
        agenda.setClienteId("ASDasdasdsa");
        agenda.setClienteEmail("testedadosfaltando@gmail.com");
        assertThat(agenda.isCadeiraLivreSemClienteVinculado()).isTrue();
    }

    @Test
    @DisplayName("Deve retornar True quando possuir todos os dados do cliente vinculado")
    public void isCadeiraLivreSemClienteVinculado_deveRetornarFalse_quandoPossuirTodosOsDadosDoCliente() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.definirSituacaoComoCancelada();
        agenda.setClienteId("asdasd515s1a51d1a5");
        agenda.setClienteNome("testedadosfaltando@gmail.com");
        agenda.setClienteEmail("testedadoscliente@gmail.com");
        agenda.setClienteCpf("10332458954");
        assertThat(agenda.isCadeiraLivreSemClienteVinculado()).isFalse();
    }

    @Test
    @DisplayName("Deve definir minutos disponíveis quando existir campo no request")
    public void definirMinutosDisponiveis_deveDefinirMinutosDisponiveis_quandoExistirNoRequest() {
        var request = umaCadeiraLivreRequest();
        request.setMinutosDisponiveis(10);
        var agenda = Agenda.of(request, umUsuarioAutenticadoAdmin(), Set.of(umServico()));
        assertThat(agenda).isNotNull();
        assertThat(agenda.getMinutosDisponiveis()).isEqualTo(10);
    }

    @Test
    @DisplayName("Deve definir 30 minutos quando não existir campo no request")
    public void definirMinutosDisponiveis_deveDefinir30Minutos_quandoNaoExistirCampoNoRequest() {
        var request = umaCadeiraLivreRequest();
        request.setMinutosDisponiveis(null);
        var agenda = Agenda.of(request, umUsuarioAutenticadoAdmin(), Set.of(umServico()));
        assertThat(agenda).isNotNull();
        assertThat(agenda.getMinutosDisponiveis()).isEqualTo(30);
    }

    @Test
    @DisplayName("Deve definir 30 minutos quando campo existir no request com valor 0")
    public void definirMinutosDisponiveis_deveDefinir30Minutos_quandoCampoInformadoNoRequestForZero() {
        var request = umaCadeiraLivreRequest();
        request.setMinutosDisponiveis(0);
        var agenda = Agenda.of(request, umUsuarioAutenticadoAdmin(), Set.of(umServico()));
        assertThat(agenda).isNotNull();
        assertThat(agenda.getMinutosDisponiveis()).isEqualTo(30);
    }

    @Test
    @DisplayName("Deve lançar exception quando campo no request com valor maior que 60")
    public void definirMinutosDisponiveis_deveLancarException_quandoCampoNoRequestForMaiorQue60() {
        var request = umaCadeiraLivreRequest();
        request.setMinutosDisponiveis(61);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> Agenda.of(request, umUsuarioAutenticadoAdmin(), Set.of(umServico())))
            .withMessage("A cadeira livre não pode ficar disponível por mais que 60 minutos.");
    }

    @Test
    @DisplayName("Deve reservar dados do cliente quando informar objeto de dados do JWT")
    public void reservarParaClientes_deveGerarDadosDoCliente_quandoInformarDadosDoJwt() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.reservarParaCliente(umJwtUsuarioResponse());
        assertThat(agenda.getClienteId()).isEqualTo("5cd48099-1009-43c4-b979-f68148a2a81d");
        assertThat(agenda.getClienteNome()).isEqualTo("Victor Hugo Negrisoli");
        assertThat(agenda.getClienteEmail()).isEqualTo("vhnegrisoli@gmail.com");
        assertThat(agenda.getClienteCpf()).isEqualTo("103.324.589-54");
    }

    @Test
    @DisplayName("Deve definir horário atual se a agenda for do tipo Cadeira Livre")
    public void definirHorarioAgendamento_deveDefinirHorarioAtual_seAgendaForTipoCadeiraLivre() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.definirHorarioAgendamento();
        var horarioAtual = LocalTime.now();
        var horaAtual = horarioAtual.getHour();
        var minutoAtual = horarioAtual.getMinute();
        assertThat(agenda.getHorarioAgendamento().getHour()).isEqualTo(horaAtual);
        assertThat(agenda.getHorarioAgendamento().getMinute()).isEqualTo(minutoAtual);
    }

    @Test
    @DisplayName("Deve definir horário especificado se a agenda for do tipo Horário Marcado")
    public void definirHorarioAgendamento_deveDefinirHorarioEspecificado_seAgendaForTipoHorarioMarcado() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.setTipoAgenda(ETipoAgenda.HORARIO_MARCADO);

        var horario = umHorario();
        horario.setHorario(LocalTime.now().plusHours(3).plusMinutes(10));

        agenda.setHorario(horario);
        agenda.definirHorarioAgendamento();

        var horarioAtual = LocalTime.now();
        var horaAtual = horarioAtual.getHour();
        var minutoAtual = horarioAtual.getMinute();
        assertThat(agenda.getHorarioAgendamento().getHour()).isNotEqualTo(horaAtual);
        assertThat(agenda.getHorarioAgendamento().getMinute()).isNotEqualTo(minutoAtual);
    }
}