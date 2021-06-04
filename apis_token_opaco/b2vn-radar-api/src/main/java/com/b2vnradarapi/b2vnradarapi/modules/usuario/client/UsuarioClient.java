package com.b2vnradarapi.b2vnradarapi.modules.usuario.client;

import com.b2vnradarapi.b2vnradarapi.modules.log.dto.LogRequest;
import com.b2vnradarapi.b2vnradarapi.modules.usuario.dto.UsuarioAutenticado;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    contextId = "usuarioClient",
    name = "usuarioAutenticadoClient",
    url = "${app-config.services.b2vn-auth-api.url}")
public interface UsuarioClient {

    @GetMapping("/api/usuarios/usuario-autenticado")
    UsuarioAutenticado getUsuarioAutenticado();

    @PostMapping("/api/log")
    void saveLog(@RequestBody LogRequest request);
}