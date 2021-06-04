package br.com.cadeiralivreempresaapi.modulos.agenda.model;

import br.com.cadeiralivreempresaapi.modulos.agenda.dto.agenda.AgendaRequest;
import br.com.cadeiralivreempresaapi.modulos.agenda.dto.cadeiralivre.CadeiraLivreRequest;
import br.com.cadeiralivreempresaapi.modulos.agenda.enums.ESituacaoAgenda;
import br.com.cadeiralivreempresaapi.modulos.agenda.enums.ETipoAgenda;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Empresa;
import br.com.cadeiralivreempresaapi.modulos.jwt.dto.JwtUsuarioResponse;
import br.com.cadeiralivreempresaapi.modulos.usuario.dto.UsuarioAutenticado;
import br.com.cadeiralivreempresaapi.modulos.usuario.model.Usuario;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

import static br.com.cadeiralivreempresaapi.modulos.agenda.messages.AgendaHorarioMessages.CADEIRA_LIVRE_MAIOR_60_MINUTOS;
import static br.com.cadeiralivreempresaapi.modulos.comum.util.Constantes.*;
import static org.springframework.util.ObjectUtils.isEmpty;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AGENDA")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_USUARIO")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "FK_EMPRESA")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "FK_HORARIO")
    private Horario horario;

    @NotNull
    @JoinTable(name = "AGENDA_SERVICOS", joinColumns = {
        @JoinColumn(name = "FK_AGENDA", foreignKey = @ForeignKey(name = "FK_AGENDA_ID"),
            referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "FK_SERVICO", foreignKey = @ForeignKey(name = "FK_SERVICO_ID"),
            referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Servico> servicos;

    @Column(name = "HORARIO_AGENDAMENTO", nullable = false)
    private LocalTime horarioAgendamento;

    @Column(name = "SITUACAO", nullable = false)
    @Enumerated(EnumType.STRING)
    private ESituacaoAgenda situacao;

    @Column(name = "DATA_CADASTRO", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "CLIENTE_ID")
    private String clienteId;

    @Column(name = "CLIENTE_NOME")
    private String clienteNome;

    @Column(name = "CLIENTE_EMAIL")
    private String clienteEmail;

    @Column(name = "CLIENTE_CPF")
    private String clienteCpf;

    @Column(name = "TOTAL_DESCONTO", nullable = false)
    private Double totalDesconto;

    @Column(name = "TOTAL_PAGAMENTO", nullable = false)
    private Double totalPagamento;

    @Column(name = "TOTAL_SERVICO", nullable = false)
    private Double totalServico;

    @Column(name = "DESCONTO")
    private Float desconto;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_AGENDA", nullable = false)
    private ETipoAgenda tipoAgenda;

    @Column(name = "MINUTOS_DISPONIVEIS", nullable = false)
    private Integer minutosDisponiveis;

    @Column(name = "TRANSACAO_ID")
    private Long transacaoId;

    public static Agenda of(AgendaRequest request) {
        return Agenda
            .builder()
            .servicos(request
                .getServicosIds()
                .stream()
                .map(Servico::new)
                .collect(Collectors.toSet()))
            .situacao(ESituacaoAgenda.RESERVA)
            .tipoAgenda(ETipoAgenda.HORARIO_MARCADO)
            .empresa(new Empresa(request.getEmpresaId()))
            .dataCadastro(LocalDateTime.now())
            .build();
    }

    public static Agenda of(CadeiraLivreRequest request,
                            UsuarioAutenticado usuario,
                            Set<Servico> servicos) {
        var agenda = Agenda
            .builder()
            .servicos(servicos)
            .horarioAgendamento(LocalTime.now())
            .usuario(new Usuario(usuario.getId()))
            .situacao(ESituacaoAgenda.DISPONIVEL)
            .desconto(request.getDesconto())
            .tipoAgenda(ETipoAgenda.CADEIRA_LIVRE)
            .empresa(new Empresa(request.getEmpresaId()))
            .minutosDisponiveis(definirMinutosDisponiveis(request))
            .dataCadastro(LocalDateTime.now())
            .build();
        agenda.calcularTotal(request.getDesconto());
        agenda.definirHorarioAgendamento();
        return agenda;
    }

    private static Integer definirMinutosDisponiveis(CadeiraLivreRequest request) {
        if (isEmpty(request.getMinutosDisponiveis()) || IntegerType.ZERO.equals(request.getMinutosDisponiveis())) {
            return TRINTA_MINUTOS.intValue();
        }
        if (request.getMinutosDisponiveis() > SESSENTA_MINUTOS) {
            throw CADEIRA_LIVRE_MAIOR_60_MINUTOS;
        }
        return request.getMinutosDisponiveis();
    }

    public void calcularTotal(Float desconto) {
        totalServico = servicos
            .stream()
            .map(Servico::getPreco)
            .mapToDouble(Double::doubleValue)
            .sum();
        totalDesconto = isEmpty(desconto)
            ? DoubleType.ZERO
            : totalServico * (desconto / PERCENTUAL);
        totalPagamento = totalServico - totalDesconto;
    }

    public boolean isCancelada() {
        return ESituacaoAgenda.CANCELADA.equals(situacao);
    }

    public boolean isDisponivel() {
        return ESituacaoAgenda.DISPONIVEL.equals(situacao);
    }

    public boolean isReservada() {
        return ESituacaoAgenda.RESERVA.equals(situacao);
    }

    public boolean isValida() {
        return informarHorarioExpiracao().isAfter(LocalTime.now())
            && isDisponivel()
            && isCadeiraLivreSemClienteVinculado();
    }

    public boolean isCadeiraLivreSemClienteVinculado() {
        return isEmpty(clienteId)
            || isEmpty(clienteNome)
            || isEmpty(clienteEmail)
            || isEmpty(clienteCpf);
    }

    public boolean isInvalida() {
        return !isValida();
    }

    public Agenda definirSituacaoComoCancelada() {
        situacao = ESituacaoAgenda.CANCELADA;
        return this;
    }

    public LocalTime informarHorarioExpiracao() {
        return dataCadastro.toLocalTime().plusMinutes(minutosDisponiveis);
    }

    public Long informarTempoRestante() {
        return LocalTime.now().until(informarHorarioExpiracao(), ChronoUnit.MINUTES);
    }

    public void reservarParaCliente(JwtUsuarioResponse cliente) {
        setClienteId(cliente.getId());
        setClienteNome(cliente.getNome());
        setClienteEmail(cliente.getEmail());
        setClienteCpf(cliente.getCpf());
        setSituacao(ESituacaoAgenda.RESERVA);
    }

    public void definirHorarioAgendamento() {
        if (ETipoAgenda.CADEIRA_LIVRE.equals(tipoAgenda)) {
            horarioAgendamento = dataCadastro.toLocalTime();
        } else {
            horarioAgendamento = horario.getHorario();
        }
    }
}