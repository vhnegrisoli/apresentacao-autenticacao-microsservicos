package br.com.cadeiralivreempresaapi.modulos.agenda.dto;

import br.com.cadeiralivreempresaapi.modulos.agenda.dto.agenda.ServicoAgendaResponse;
import br.com.cadeiralivreempresaapi.modulos.agenda.dto.cadeiralivre.CadeiraLivreResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Collections;

import static br.com.cadeiralivreempresaapi.modulos.agenda.mocks.AgendaMocks.umaAgendaCadeiraLivre;
import static br.com.cadeiralivreempresaapi.modulos.comum.util.NumeroUtil.converterParaDuasCasasDecimais;
import static br.com.cadeiralivreempresaapi.modulos.jwt.mocks.JwtMocks.umJwtUsuarioResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class CadeiraLivreResponseTest {

    @Test
    @DisplayName("Deve converter para cadeira livre response quando informar objeto Agenda do tipo cadeira livre")
    public void of_deveConverterParaCadeiraLivreResponse_quandoInformarUmaAgendaDoTipoCadeiraLivre() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.calcularTotal(20f);
        agenda.definirHorarioAgendamento();
        agenda.setMinutosDisponiveis(20);
        var response = CadeiraLivreResponse.of(agenda);
        var horarioAtual = LocalTime.now();
        assertThat(response).isNotNull();
        assertThat(response.getHorario().getHour()).isEqualTo(horarioAtual.getHour());
        assertThat(response.getHorario().getMinute()).isEqualTo(horarioAtual.getMinute());
        assertThat(response.getServicos().get(0)).isEqualTo(new ServicoAgendaResponse(1, "Corte de cabelo", 25.0));
        assertThat(response.getEmpresaId()).isEqualTo(1);
        assertThat(response.getEmpresaNome()).isEqualTo("Empresa 01");
        assertThat(response.getEmpresaCnpj()).isEqualTo("82.765.926/0001-32");
        assertThat(response.getDesconto()).isEqualTo(converterParaDuasCasasDecimais(23.10));
        assertThat(response.getTotalServico()).isEqualTo(converterParaDuasCasasDecimais(25.00));
        assertThat(response.getTotalDesconto()).isEqualTo(converterParaDuasCasasDecimais(5.00));
        assertThat(response.getTotalPagamento()).isEqualTo(converterParaDuasCasasDecimais(20.00));
        assertThat(response.getMinutosDisponiveis()).isEqualTo(20);
        assertThat(response.getHorarioExpiracao().getMinute()).isEqualTo(horarioAtual.plusMinutes(20).getMinute());
        assertThat(response.getCadeiraLivreValida()).isTrue();
        assertThat(response.getSituacao()).isEqualTo("Disponível");
        assertThat(response.getCliente()).isNull();
    }

    @Test
    @DisplayName("Deve converter para response com situação reservada quando informar cadeira livre com cliente vinculado")
    public void of_deveConverterParaResponseReservada_quandoInformarUmaCadeiraLivreComClienteVinculado() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.calcularTotal(20f);
        agenda.definirHorarioAgendamento();
        agenda.setMinutosDisponiveis(20);
        agenda.reservarParaCliente(umJwtUsuarioResponse());
        var response = CadeiraLivreResponse.of(agenda);
        var horarioAtual = LocalTime.now();
        assertThat(response).isNotNull();
        assertThat(response.getHorario().getHour()).isEqualTo(horarioAtual.getHour());
        assertThat(response.getHorario().getMinute()).isEqualTo(horarioAtual.getMinute());
        assertThat(response.getServicos().get(0)).isEqualTo(new ServicoAgendaResponse(1, "Corte de cabelo", 25.0));
        assertThat(response.getEmpresaId()).isEqualTo(1);
        assertThat(response.getEmpresaNome()).isEqualTo("Empresa 01");
        assertThat(response.getEmpresaCnpj()).isEqualTo("82.765.926/0001-32");
        assertThat(response.getDesconto()).isEqualTo(converterParaDuasCasasDecimais(23.10));
        assertThat(response.getTotalServico()).isEqualTo(converterParaDuasCasasDecimais(25.00));
        assertThat(response.getTotalDesconto()).isEqualTo(converterParaDuasCasasDecimais(5.00));
        assertThat(response.getTotalPagamento()).isEqualTo(converterParaDuasCasasDecimais(20.00));
        assertThat(response.getMinutosDisponiveis()).isNull();
        assertThat(response.getHorarioExpiracao()).isNull();
        assertThat(response.getCadeiraLivreValida()).isFalse();
        assertThat(response.getSituacao()).isEqualTo("Reservada");
        assertThat(response.getCliente().getId()).isEqualTo("5cd48099-1009-43c4-b979-f68148a2a81d");
        assertThat(response.getCliente().getNome()).isEqualTo("Victor Hugo Negrisoli");
        assertThat(response.getCliente().getEmail()).isEqualTo("vhnegrisoli@gmail.com");
        assertThat(response.getCliente().getCpf()).isEqualTo("103.324.589-54");
    }

    @Test
    @DisplayName("Deve converter para response com lista de serviços vazia quando não informar serviços")
    public void of_deveConverterParaResponseSemServicos_quandoInformarListaDeServicosVazia() {
        var agenda = umaAgendaCadeiraLivre();
        agenda.calcularTotal(20f);
        agenda.definirHorarioAgendamento();
        agenda.setMinutosDisponiveis(20);
        agenda.reservarParaCliente(umJwtUsuarioResponse());
        agenda.setServicos(Collections.emptySet());
        var response = CadeiraLivreResponse.of(agenda);
        var horarioAtual = LocalTime.now();
        assertThat(response).isNotNull();
        assertThat(response.getHorario().getHour()).isEqualTo(horarioAtual.getHour());
        assertThat(response.getHorario().getMinute()).isEqualTo(horarioAtual.getMinute());
        assertThat(response.getServicos().isEmpty()).isTrue();
        assertThat(response.getEmpresaId()).isEqualTo(1);
        assertThat(response.getEmpresaNome()).isEqualTo("Empresa 01");
        assertThat(response.getEmpresaCnpj()).isEqualTo("82.765.926/0001-32");
        assertThat(response.getDesconto()).isEqualTo(converterParaDuasCasasDecimais(23.10));
        assertThat(response.getTotalServico()).isEqualTo(converterParaDuasCasasDecimais(25.00));
        assertThat(response.getTotalDesconto()).isEqualTo(converterParaDuasCasasDecimais(5.00));
        assertThat(response.getTotalPagamento()).isEqualTo(converterParaDuasCasasDecimais(20.00));
        assertThat(response.getMinutosDisponiveis()).isNull();
        assertThat(response.getHorarioExpiracao()).isNull();
        assertThat(response.getCadeiraLivreValida()).isFalse();
        assertThat(response.getSituacao()).isEqualTo("Reservada");
        assertThat(response.getCliente().getId()).isEqualTo("5cd48099-1009-43c4-b979-f68148a2a81d");
        assertThat(response.getCliente().getNome()).isEqualTo("Victor Hugo Negrisoli");
        assertThat(response.getCliente().getEmail()).isEqualTo("vhnegrisoli@gmail.com");
        assertThat(response.getCliente().getCpf()).isEqualTo("103.324.589-54");
    }
}
