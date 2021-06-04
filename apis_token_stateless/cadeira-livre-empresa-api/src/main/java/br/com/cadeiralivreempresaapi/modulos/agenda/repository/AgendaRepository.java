package br.com.cadeiralivreempresaapi.modulos.agenda.repository;

import br.com.cadeiralivreempresaapi.modulos.agenda.enums.ESituacaoAgenda;
import br.com.cadeiralivreempresaapi.modulos.agenda.enums.ETipoAgenda;
import br.com.cadeiralivreempresaapi.modulos.agenda.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

    Boolean existsByHorarioId(Integer horarioId);

    Boolean existsByServicosId(Integer servicoId);

    List<Agenda> findByEmpresaIdAndTipoAgenda(Integer empresaId, ETipoAgenda tipoAgenda);

    Optional<Agenda> findByIdAndEmpresaIdAndTipoAgenda(Integer id, Integer empresaId, ETipoAgenda tipoAgenda);

    List<Agenda> findBySituacao(ESituacaoAgenda situacao);

    List<Agenda> findByEmpresaIdAndSituacao(Integer empresaId, ESituacaoAgenda situacao);

    List<Agenda> findByClienteIdAndTipoAgenda(String clienteId, ETipoAgenda tipoAgenda);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Agenda a SET"
        + " a.clienteId = :clienteId,"
        + " a.clienteNome = :clienteNome,"
        + " a.clienteEmail = :clienteEmail,"
        + " a.clienteCpf = :clienteCpf,"
        + " a.situacao = :situacao"
        + " WHERE a.id = :id"
    )
    @SuppressWarnings("ParameterNumber")
    void reservarAgendaParaCliente(Integer id,
                                   String clienteId,
                                   String clienteNome,
                                   String clienteEmail,
                                   String clienteCpf,
                                   ESituacaoAgenda situacao);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Agenda a SET"
        + " a.transacaoId = :transacaoId"
        + " WHERE a.id = :id"
    )
    void definirTransacaoIdParaCadeiraLivre(Long transacaoId, Integer id);
}
