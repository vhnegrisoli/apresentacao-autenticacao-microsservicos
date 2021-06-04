package br.com.cadeiralivreempresaapi.modulos.jwt.controller;

import br.com.cadeiralivreempresaapi.modulos.jwt.dto.JwtUsuarioResponse;
import br.com.cadeiralivreempresaapi.modulos.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @GetMapping("validar/{jwt}")
    public Boolean validarToken(@PathVariable String jwt) {
        return jwtService.verificarUsuarioValidoComTokenValida(jwt);
    }

    @GetMapping("dados/{jwt}")
    public JwtUsuarioResponse recuperarDadosDoUsuarioDoToken(@PathVariable String jwt) {
        return jwtService.recuperarDadosDoUsuarioDoToken(jwt);
    }

    @DeleteMapping("invalidas/remover-todas")
    public void removerTokensInvalidas() {
        jwtService.removerTokensInvalidas(true);
    }
}