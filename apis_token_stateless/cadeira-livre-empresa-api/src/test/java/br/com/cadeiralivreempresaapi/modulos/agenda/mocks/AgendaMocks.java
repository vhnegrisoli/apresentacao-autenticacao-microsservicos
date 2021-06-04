package br.com.cadeiralivreempresaapi.modulos.agenda.mocks;

import br.com.cadeiralivreempresaapi.modulos.agenda.dto.agenda.AgendaRequest;
import br.com.cadeiralivreempresaapi.modulos.agenda.dto.cadeiralivre.CadeiraLivreRequest;
import br.com.cadeiralivreempresaapi.modulos.agenda.dto.cadeiralivre.CadeiraLivreReservaRequest;
import br.com.cadeiralivreempresaapi.modulos.agenda.dto.cadeiralivre.CadeiraLivreResponse;
import br.com.cadeiralivreempresaapi.modulos.agenda.enums.ESituacaoAgenda;
import br.com.cadeiralivreempresaapi.modulos.agenda.enums.ETipoAgenda;
import br.com.cadeiralivreempresaapi.modulos.agenda.model.Agenda;
import br.com.cadeiralivreempresaapi.modulos.transacao.dto.TransacaoResponse;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static br.com.cadeiralivreempresaapi.modulos.agenda.mocks.ClienteMocks.umClienteRequest;
import static br.com.cadeiralivreempresaapi.modulos.agenda.mocks.HorarioMocks.umHorario;
import static br.com.cadeiralivreempresaapi.modulos.agenda.mocks.ServicoMocks.umServico;
import static br.com.cadeiralivreempresaapi.modulos.empresa.mocks.EmpresaMocks.umaEmpresa;
import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.umUsuario;

public class AgendaMocks {

    public static Agenda umaAgendaHorarioMarcado() {
        var cliente = umClienteRequest();
        return Agenda
            .builder()
            .id(1)
            .horario(umHorario())
            .servicos(Set.of(umServico()))
            .situacao(ESituacaoAgenda.RESERVA)
            .tipoAgenda(ETipoAgenda.HORARIO_MARCADO)
            .clienteId(cliente.getId())
            .clienteCpf(cliente.getCpf())
            .clienteEmail(cliente.getEmail())
            .clienteNome(cliente.getNome())
            .desconto(null)
            .dataCadastro(LocalDateTime.now())
            .empresa(umaEmpresa())
            .horarioAgendamento(LocalTime.of(12, 0))
            .usuario(umUsuario())
            .build();
    }

    public static Agenda umaAgendaCadeiraLivre() {
        return Agenda
            .builder()
            .id(1)
            .horario(null)
            .servicos(Collections.singleton(umServico()))
            .situacao(ESituacaoAgenda.DISPONIVEL)
            .tipoAgenda(ETipoAgenda.CADEIRA_LIVRE)
            .desconto(23.1f)
            .dataCadastro(LocalDateTime.now())
            .horarioAgendamento(LocalTime.now())
            .minutosDisponiveis(30)
            .empresa(umaEmpresa())
            .horarioAgendamento(LocalTime.of(12, 0))
            .usuario(umUsuario())
            .totalServico(25.00)
            .totalPagamento(25.00)
            .totalDesconto(12.00)
            .minutosDisponiveis(20)
            .build();
    }

    public static AgendaRequest umaAgendaRequest() {
        return AgendaRequest
            .builder()
            .horarioId(1)
            .empresaId(1)
            .servicosIds(List.of(1))
            .build();
    }

    public static CadeiraLivreRequest umaCadeiraLivreRequest() {
        return CadeiraLivreRequest
            .builder()
            .servicosIds(List.of(1))
            .desconto(30.0f)
            .empresaId(1)
            .minutosDisponiveis(20)
            .build();
    }

    public static CadeiraLivreReservaRequest umaCadeiraLivreReservaRequest() {
        return CadeiraLivreReservaRequest
            .builder()
            .token("123")
            .cadeiraLivreId(1)
            .cartaoId("casdasd")
            .build();
    }

    public static CadeiraLivreResponse umaCadeiraLivreResponse() {
        return CadeiraLivreResponse.of(umaAgendaCadeiraLivre());
    }

    public static TransacaoResponse umaTransacaoResponse() {
        return TransacaoResponse
            .builder()
            .transacaoPagarmeId(151651L)
            .build();
    }

}
