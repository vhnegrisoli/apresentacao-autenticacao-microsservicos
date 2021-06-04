package br.com.cadeiralivreempresaapi.modulos.agenda.service;

import br.com.cadeiralivreempresaapi.modulos.agenda.dto.cadeiralivre.CadeiraLivreRequest;
import br.com.cadeiralivreempresaapi.modulos.agenda.dto.cadeiralivre.CadeiraLivreReservaRequest;
import br.com.cadeiralivreempresaapi.modulos.agenda.dto.cadeiralivre.CadeiraLivreResponse;
import br.com.cadeiralivreempresaapi.modulos.agenda.enums.ESituacaoAgenda;
import br.com.cadeiralivreempresaapi.modulos.agenda.enums.ETipoAgenda;
import br.com.cadeiralivreempresaapi.modulos.agenda.model.Agenda;
import br.com.cadeiralivreempresaapi.modulos.agenda.repository.AgendaRepository;
import br.com.cadeiralivreempresaapi.modulos.comum.response.SuccessResponseDetails;
import br.com.cadeiralivreempresaapi.modulos.comum.util.Constantes;
import br.com.cadeiralivreempresaapi.modulos.empresa.service.EmpresaService;
import br.com.cadeiralivreempresaapi.modulos.jwt.dto.JwtUsuarioResponse;
import br.com.cadeiralivreempresaapi.modulos.jwt.service.JwtService;
import br.com.cadeiralivreempresaapi.modulos.notificacao.dto.NotificacaoCorpoRequest;
import br.com.cadeiralivreempresaapi.modulos.notificacao.service.NotificacaoService;
import br.com.cadeiralivreempresaapi.modulos.transacao.dto.TransacaoResponse;
import br.com.cadeiralivreempresaapi.modulos.transacao.service.TransacaoService;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.AutenticacaoService;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.UsuarioAcessoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.cadeiralivreempresaapi.modulos.agenda.messages.AgendaHorarioMessages.*;
import static br.com.cadeiralivreempresaapi.modulos.jwt.messages.JwtMessages.USUARIO_NAO_AUTENTICADO;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class CadeiraLivreService {

    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private AgendaService agendaService;
    @Autowired
    private AutenticacaoService autenticacaoService;
    @Autowired
    private NotificacaoService notificacaoService;
    @Autowired
    private ServicoService servicoService;
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private UsuarioAcessoService acessoService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private TransacaoService transacaoService;

    @Transactional
    public CadeiraLivreResponse disponibilizarCadeiraLivre(CadeiraLivreRequest request) {
        validarDadosCadeiraLivre(request);
        var agenda = Agenda.of(request,
            autenticacaoService.getUsuarioAutenticado(),
            servicoService.buscarServicosPorIds(request.getServicosIds()));
        validarEmpresasServicosEUsuario(request, agenda);
        agenda.calcularTotal(request.getDesconto());
        var agendaSalva = agendaRepository.save(agenda);
        agendaSalva = agendaService.buscarAgendaPorId(agendaSalva.getId());
        enviarDadosNotificacaoCadeiraLivre(agendaSalva);
        return CadeiraLivreResponse.of(agendaSalva);
    }

    private void validarDadosCadeiraLivre(CadeiraLivreRequest request) {
        if (isEmpty(request)) {
            throw AGENDA_SEM_DADOS;
        }
        if (isEmpty(request.getDesconto())) {
            throw CADEIRA_LIVRE_SEM_DESCONTO;
        }
        if (isEmpty(request.getServicosIds())) {
            throw AGENDA_SEM_SERVICOS;
        }
        if (isEmpty(request.getEmpresaId())) {
            throw AGENDA_SEM_EMPRESA;
        }
    }

    private void validarEmpresasServicosEUsuario(CadeiraLivreRequest request,
                                                 Agenda agenda) {
        acessoService.validarPermissoesDoUsuario(request.getEmpresaId());
        agenda.setEmpresa(empresaService.buscarPorId(request.getEmpresaId()));
        servicoService.validarServicosExistentesPorEmpresa(new ArrayList<>(agenda.getServicos()), agenda.getEmpresa().getId());
    }

    private void enviarDadosNotificacaoCadeiraLivre(Agenda agendaSalva) {
        notificacaoService.gerarDadosNotificacao(NotificacaoCorpoRequest.of(
            gerarMensagemNotificacaoCadeiraLivre(agendaSalva),
            Constantes.NOVA_CADEIRA_LIVRE_NOTIFICACAO,
            Constantes.TOPIC_TODOS_CADEIRA_LIVRE
            )
        );
    }

    private String gerarMensagemNotificacaoCadeiraLivre(Agenda agenda) {
        return "Nova Cadeira Livre liberada para "
            .concat(agenda.getEmpresa().getNome())
            .concat(" oferecendo serviços de ")
            .concat(servicoService.tratarNomesServicos(new ArrayList<>(agenda.getServicos())))
            .concat(" com ")
            .concat(String.valueOf(agenda.getDesconto()))
            .concat("% de desconto!");
    }

    public List<CadeiraLivreResponse> buscarCadeirasLivresDisponiveis(String jwtToken, Integer empresaId) {
        validarClienteComJwtValido(jwtToken);
        var cadeirasLivres = isEmpty(empresaId)
            ? agendaRepository.findBySituacao(ESituacaoAgenda.DISPONIVEL)
            : agendaRepository.findByEmpresaIdAndSituacao(empresaId, ESituacaoAgenda.DISPONIVEL);
        return cadeirasLivres
            .stream()
            .filter(Agenda::isValida)
            .map(CadeiraLivreResponse::of)
            .collect(Collectors.toList());
    }

    public List<CadeiraLivreResponse> buscarCadeirasLivresDoCliente(String jwtToken) {
        validarClienteComJwtValido(jwtToken);
        var cliente = jwtService.recuperarDadosDoUsuarioDoToken(jwtToken);
        return agendaRepository.findByClienteIdAndTipoAgenda(cliente.getId(), ETipoAgenda.CADEIRA_LIVRE)
            .stream()
            .map(CadeiraLivreResponse::of)
            .collect(Collectors.toList());
    }

    public CadeiraLivreResponse buscarCadeiraLivrePorId(Integer id, String jwtToken) {
        validarClienteComJwtValido(jwtToken);
        var cliente = jwtService.recuperarDadosDoUsuarioDoToken(jwtToken);
        var cadeiraLivre = agendaService.buscarAgendaPorId(id);
        if (!isEmpty(cadeiraLivre.getClienteId())
            && !cadeiraLivre.getClienteId().equals(cliente.getId())
            || cadeiraLivre.isCancelada()) {
            throw CADEIRA_LIVRE_SEM_PERMISSAO_VISUALIZAR;
        }
        return CadeiraLivreResponse.of(cadeiraLivre,
            transacaoService.buscarTransacao(cadeiraLivre.getTransacaoId(), jwtToken));
    }

    public List<CadeiraLivreResponse> buscarCadeirasLivresPorEmpresa(Integer empresaId) {
        acessoService.validarPermissoesDoUsuario(empresaId);
        return agendaRepository.findByEmpresaIdAndTipoAgenda(empresaId, ETipoAgenda.CADEIRA_LIVRE)
            .stream()
            .map(CadeiraLivreResponse::of)
            .collect(Collectors.toList());
    }

    public CadeiraLivreResponse buscarCadeiraLivreResponsePorIdEPorEmpresaId(Integer id, Integer empresaId) {
        return CadeiraLivreResponse.of(buscarCadeiraLivrePorIdEPorEmpresaId(id, empresaId));
    }

    public Agenda buscarCadeiraLivrePorIdEPorEmpresaId(Integer id, Integer empresaId) {
        acessoService.validarPermissoesDoUsuario(empresaId);
        return agendaRepository
            .findByIdAndEmpresaIdAndTipoAgenda(id, empresaId, ETipoAgenda.CADEIRA_LIVRE)
            .orElseThrow(() -> CADEIRA_LIVRE_NAO_ENCONTRADA);
    }

    @Transactional
    public SuccessResponseDetails indisponibilizarCadeiraLivre(Integer id, Integer empresaId) {
        var cadeiraLivre = buscarCadeiraLivrePorIdEPorEmpresaId(id, empresaId);
        validarCadeiraLivreComClienteAtribuido(cadeiraLivre);
        cadeiraLivre.definirSituacaoComoCancelada();
        agendaRepository.save(cadeiraLivre);
        return new SuccessResponseDetails("A cadeira livre foi indisponibilizada com sucesso.");
    }

    private void validarCadeiraLivreComClienteAtribuido(Agenda agenda) {
        if (!isEmpty(agenda.getClienteId())) {
            throw CADEIRA_LIVRE_COM_CLIENTE;
        }
    }

    @Transactional
    public void indisponibilizarCadeirasLivresExpiradas(Boolean precisaDeAutenticacao) {
        if (precisaDeAutenticacao && !autenticacaoService.getUsuarioAutenticado().isAdmin()) {
            throw CADEIRA_LIVRE_SEM_PERMISSAO_INDISPONIBILIZAR;
        }
        var cadeirasLivresIndisponibilizar = agendaRepository
            .findBySituacao(ESituacaoAgenda.DISPONIVEL)
            .stream()
            .filter(Agenda::isInvalida)
            .map(Agenda::definirSituacaoComoCancelada)
            .collect(Collectors.toList());
        if (!isEmpty(cadeirasLivresIndisponibilizar)) {
            agendaRepository.saveAll(cadeirasLivresIndisponibilizar);
            log.info("Foram indisponibilizadas {} cadeiras livres com tempo expirado.", cadeirasLivresIndisponibilizar.size());
        } else {
            log.info("Não foram encontradas cadeiras livres disponíveis com tempo expirado.");
        }
    }

    public CadeiraLivreResponse reservarCadeiraLivreParaCliente(Integer id, String token, String cartaoId) {
        return reservarCadeiraLivre(CadeiraLivreReservaRequest
            .builder()
            .cadeiraLivreId(id)
            .token(token)
            .cartaoId(cartaoId)
            .build());
    }

    public CadeiraLivreResponse reservarCadeiraLivre(CadeiraLivreReservaRequest request) {
        validarClienteComJwtValido(request.getToken());
        var cliente = jwtService.recuperarDadosDoUsuarioDoToken(request.getToken());
        var cadeiraLivre = agendaService.buscarAgendaPorId(request.getCadeiraLivreId());
        validarCadeiraLivreInvalidaParaReserva(cadeiraLivre);
        validarClienteComDadosIncompletos(cliente);
        var transacao = tratarTransacaoCadeiraLivre(cadeiraLivre, cliente, request);
        reservarAgendaParaCliente(cadeiraLivre, cliente);
        return CadeiraLivreResponse.of(cadeiraLivre, transacao);
    }

    private TransacaoResponse tratarTransacaoCadeiraLivre(Agenda cadeiraLivre,
                                                             JwtUsuarioResponse cliente,
                                                             CadeiraLivreReservaRequest request) {
        var transacao = transacaoService
            .realizarTransacao(cadeiraLivre, request.getCartaoId(), cliente, request.getToken());
        definirTransacaoParaCadeiraLivre(cadeiraLivre, transacao);
        return transacao;
    }

    @Transactional
    private void reservarAgendaParaCliente(Agenda cadeiraLivre, JwtUsuarioResponse cliente) {
        cadeiraLivre.reservarParaCliente(cliente);
        agendaRepository.reservarAgendaParaCliente(cadeiraLivre.getId(),
            cadeiraLivre.getClienteId(),
            cadeiraLivre.getClienteNome(),
            cadeiraLivre.getClienteEmail(),
            cadeiraLivre.getClienteCpf(),
            ESituacaoAgenda.RESERVA);
    }

    @Transactional
    private void definirTransacaoParaCadeiraLivre(Agenda cadeiraLivre,
                                                    TransacaoResponse transacaoResponse) {
        cadeiraLivre.setTransacaoId(transacaoResponse.getTransacaoPagarmeId());
        agendaRepository.definirTransacaoIdParaCadeiraLivre(transacaoResponse.getTransacaoPagarmeId(), cadeiraLivre.getId());
    }

    private void validarCadeiraLivreInvalidaParaReserva(Agenda cadeiraLivre) {
        if (!cadeiraLivre.isValida()) {
            throw CADEIRA_LIVRE_INDISPONIVEL;
        }
    }

    private void validarClienteComDadosIncompletos(JwtUsuarioResponse cliente) {
        if (isEmpty(cliente.getId())
            || isEmpty(cliente.getNome())
            || isEmpty(cliente.getEmail())
            || isEmpty(cliente.getCpf())) {
            throw CADEIRA_LIVRE_DADOS_INCOMPLETOS;
        }
    }

    private void validarClienteComJwtValido(String jwtToken) {
        if (!jwtService.verificarUsuarioValidoComTokenValida(jwtToken)) {
            throw USUARIO_NAO_AUTENTICADO;
        }
    }
}