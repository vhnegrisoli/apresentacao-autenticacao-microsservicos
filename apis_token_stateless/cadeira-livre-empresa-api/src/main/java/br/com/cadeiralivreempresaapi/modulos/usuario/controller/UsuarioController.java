package br.com.cadeiralivreempresaapi.modulos.usuario.controller;

import br.com.cadeiralivreempresaapi.modulos.comum.response.SuccessResponseDetails;
import br.com.cadeiralivreempresaapi.modulos.usuario.dto.TokenResponse;
import br.com.cadeiralivreempresaapi.modulos.usuario.dto.UsuarioAutenticado;
import br.com.cadeiralivreempresaapi.modulos.usuario.dto.UsuarioRequest;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.AutenticacaoService;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private static final String AUTHORIZATION = "authorization";

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AutenticacaoService autenticacaoService;

    @GetMapping("check-session")
    public String checkSession() {
        return "O usuário " + autenticacaoService.getUsuarioAutenticado().getNome() + " está logado.";
    }

    @GetMapping("logout")
    public SuccessResponseDetails logout(HttpServletRequest request) {
        return autenticacaoService.logout(request);
    }

    @PostMapping("proprietario")
    public SuccessResponseDetails salvarProprietario(@RequestBody UsuarioRequest usuarioRequest) {
        return usuarioService.salvarProprietario(usuarioRequest);
    }

    @PostMapping("socio/empresa/{empresaId}")
    public SuccessResponseDetails salvarSocio(@RequestBody UsuarioRequest usuarioRequest,
                                               @PathVariable Integer empresaId) {
        return usuarioService.salvarSocio(usuarioRequest, empresaId);
    }

    @PostMapping("funcionario/empresa/{empresaId}")
    public SuccessResponseDetails salvarFuncionario(@RequestBody UsuarioRequest usuarioRequest,
                                                    @PathVariable Integer empresaId) {
        return usuarioService.salvarFuncionario(usuarioRequest, empresaId);
    }

    @PutMapping("{id}/alterar-acesso")
    public SuccessResponseDetails alterarDadosUsuario(@RequestBody UsuarioRequest usuarioRequest,
                                                      @PathVariable Integer id) {
        return usuarioService.editarDadosUsuario(usuarioRequest, id);
    }

    @GetMapping("get-token")
    public TokenResponse getAuthorizationToken(@RequestHeader Map<String, String> headers) {
        return new TokenResponse(headers.get(AUTHORIZATION));
    }

    @GetMapping("usuario-autenticado")
    public UsuarioAutenticado getUsuarioAutenticado() {
        return usuarioService.getUsuarioAutenticadoAtualizaUltimaData();
    }

    @GetMapping("is-authenticated")
    public boolean verificarSeEstaAutenticado() {
        return autenticacaoService.existeUsuarioAutenticado();
    }

    @PutMapping("atualizar-token-notificacao")
    public SuccessResponseDetails atualizarTokenNotificacao(@RequestParam("token") String token) {
        return usuarioService.atualizarTokenNotificacao(token);
    }

    @PutMapping("{id}/alterar-situacao")
    public SuccessResponseDetails alterarSituacao(@PathVariable Integer id) {
        return usuarioService.alterarSituacao(id);
    }
}
