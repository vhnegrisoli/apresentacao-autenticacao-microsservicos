package br.com.b2vnauthapi.b2vnauthapi.modules.usuario.controller;

import br.com.b2vnauthapi.b2vnauthapi.modules.log.service.LogService;
import br.com.b2vnauthapi.b2vnauthapi.modules.usuario.dto.UsuarioAdminRequest;
import br.com.b2vnauthapi.b2vnauthapi.modules.usuario.dto.UsuarioAutenticado;
import br.com.b2vnauthapi.b2vnauthapi.modules.usuario.dto.UsuarioRequest;
import br.com.b2vnauthapi.b2vnauthapi.modules.usuario.dto.UsuarioResponse;
import br.com.b2vnauthapi.b2vnauthapi.modules.usuario.model.Usuario;
import br.com.b2vnauthapi.b2vnauthapi.modules.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private LogService logService;

    @GetMapping
    public List<UsuarioResponse> getUsuarios(HttpServletRequest request) throws IOException {
        var usuarios = usuarioService.getUsuarios();
        logService.gerarLogUsuario(request);
        return usuarios;
    }

    @GetMapping(value = "/page")
    public Page<Usuario> getUsuarios(@PathParam("page") Integer page,
                                     @PathParam("size") Integer size) {
        return usuarioService.getUsuariosPaginado(page, size);
    }

    @GetMapping(value = "/check-session")
    public ResponseEntity checkSession() {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping(value = "/novo")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "Usuário inserido com sucesso!")
    public void novoUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        usuarioService.save(usuarioRequest);
    }

    @PutMapping(value = "/alterar-acesso")
    @ResponseStatus(code = HttpStatus.OK, reason = "Usuário alterado com sucesso!")
    public void alterarDadosUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        usuarioService.save(usuarioRequest);
    }

    @GetMapping(value = "/usuario-autenticado")
    public UsuarioAutenticado getUsuarioAutenticado(HttpServletRequest request) throws IOException {
        return usuarioService.getUsuarioAutenticadoAtualizaUltimaData();
    }

    @PostMapping(value = "/admin/novo")
    @ResponseStatus(value = HttpStatus.OK, reason = "O usuário tornou-se um administrador!")
    public void tornarAdmin(@RequestBody UsuarioAdminRequest usuarioAdminRequest, HttpServletRequest request)
        throws IOException {
        usuarioService.tornarAdmin(usuarioAdminRequest);
    }
}
