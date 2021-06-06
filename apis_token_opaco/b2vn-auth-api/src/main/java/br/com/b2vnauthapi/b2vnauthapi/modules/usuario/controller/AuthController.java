package br.com.b2vnauthapi.b2vnauthapi.modules.usuario.controller;

import br.com.b2vnauthapi.b2vnauthapi.modules.usuario.client.AuthClient;
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
            "b2vn-auth-api-client",
            "b2vn-auth-api-secret",
            email,
            senha,
            "password",
            "application/x-www-form-urlencoded"
        );
    }
}
