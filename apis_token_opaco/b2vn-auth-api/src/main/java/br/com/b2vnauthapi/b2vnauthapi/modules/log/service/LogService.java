package br.com.b2vnauthapi.b2vnauthapi.modules.log.service;

import br.com.b2vnauthapi.b2vnauthapi.modules.log.model.Log;
import br.com.b2vnauthapi.b2vnauthapi.modules.log.repository.LogRepository;
import br.com.b2vnauthapi.b2vnauthapi.modules.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static br.com.b2vnauthapi.b2vnauthapi.modules.log.enums.ETipoOperacao.ALTERANDO;
import static br.com.b2vnauthapi.b2vnauthapi.modules.log.enums.ETipoOperacao.SALVANDO;
import static br.com.b2vnauthapi.b2vnauthapi.modules.log.enums.ETipoOperacao.CONSULTANDO;
import static br.com.b2vnauthapi.b2vnauthapi.modules.log.enums.ETipoOperacao.REMOVENDO;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@SuppressWarnings("PMD.TooManyStaticImports")
public class LogService {

    private static final String SERVICO_NOME = "B2VN_AUTH_API";
    private static final String SERVICO_DESCRICAO = "Api de Autenticação";
    private static final List<String> URLS_PROIBIDAS = List.of("usuarios", "log");

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private LogRepository logRepository;

    public void gerarLogUsuario(HttpServletRequest request) throws IOException {
        if (isAuthenticated(request) && !URLS_PROIBIDAS.contains(request.getRequestURI())) {
            var usuarioLogado = usuarioService.getUsuarioAutenticado();
            processarLogDeUsuario(Log
                .builder()
                .dataAcesso(LocalDateTime.now())
                .tipoOperacao(definirTipoAcesso(request.getMethod()))
                .metodo(request.getMethod())
                .urlAcessada(request.getRequestURI())
                .usuarioId(usuarioLogado.getId())
                .usuarioNome(usuarioLogado.getNome())
                .usuarioEmail(usuarioLogado.getEmail())
                .usuarioPermissao(usuarioLogado.getPermissao().name())
                .usuarioDescricao(usuarioLogado.getDescricao())
                .servicoNome(SERVICO_NOME)
                .servicoDescricao(SERVICO_DESCRICAO)
                .build());
        }
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        return !isEmpty(request.getUserPrincipal());
    }

    private String definirTipoAcesso(String metodo) {
        if (metodo.equals(GET.name())) {
            return CONSULTANDO.name();
        } else if (metodo.equals(POST.name())) {
            return SALVANDO.name();
        } else if (metodo.equals(PUT.name())) {
            return ALTERANDO.name();
        } else {
            return REMOVENDO.name();
        }
    }

    public void processarLogDeUsuario(Log log) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:MM:ss");
        System.out.println("Salvando o log do usuário " + log.getUsuarioNome() + " ("
            + log.getUsuarioEmail() + ") às " + df.format(log.getDataAcesso()));
        logRepository.save(log);
    }

    public Page<Log> buscarTodosPaginados(String metodo, Integer page, Integer size) {
        var pageRequest = PageRequest.of(page, size);
        return logRepository.findByMetodo(metodo, pageRequest);
    }
}
