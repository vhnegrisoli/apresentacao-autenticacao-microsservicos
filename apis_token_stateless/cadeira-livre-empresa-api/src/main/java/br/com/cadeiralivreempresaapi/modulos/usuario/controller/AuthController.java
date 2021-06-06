package br.com.cadeiralivreempresaapi.modulos.usuario.controller;

import br.com.cadeiralivreempresaapi.modulos.usuario.client.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/token")
public class AuthController {

    @Autowired
    private AuthClient authClient;

    @PostMapping
    public Object generateAccessToken(@RequestParam(name = "email") String email,
                                      @RequestParam(name = "senha") String senha) {
        return authClient.generateAccessToken(
            "cadeira-livre-empresa-api-client",
            "cadeira-livre-empresa-api-secret",
            email,
            senha,
            "password",
            "application/x-www-form-urlencoded"
        );
    }
}
